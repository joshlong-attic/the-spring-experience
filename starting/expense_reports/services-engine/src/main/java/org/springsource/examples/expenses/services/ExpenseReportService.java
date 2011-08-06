package org.springsource.examples.expenses.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.expenses.model.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Service charged with managing the approval, routing and creation of expense reports.
 *
 * @author Josh Long
 */
@Service
public class ExpenseReportService {
	private Log log = LogFactory.getLog(getClass());

	/**
	 * callback for all operations that need to systematically ascend the authorization chain, e.g., from employee to CEO
	 */
	public static interface AuthorizationChainIterationCallback {
		boolean ascendTheChainOfAuthorization(ExpenseHolder current, ExpenseHolder superior);
	}

	// well known states for an ExpenseReport to be in
	public static final String EXPENSE_REPORT_STATE_DRAFT = "DRAFT";
	public static final String EXPENSE_REPORT_STATE_FINAL = "FINAL";
	public static final String EXPENSE_REPORT_STATE_ERROR = "ERROR";

	@PersistenceContext private EntityManager entityManager;

	@Inject private ManagedFileService managedFileService;

	@Inject private ExpenseHolderService expenseHolderService;

	@Inject private ChargeBatchService chargeBatchService;

	@Transactional
	public ExpenseReport createExpenseReport(long expenseHolderId) {
		ExpenseHolder eh = expenseHolderService.getExpenseHolderById(expenseHolderId);
		ExpenseReport expenseReport = new ExpenseReport();
		expenseReport.setExpenseHolder(eh);
		expenseReport.setState(EXPENSE_REPORT_STATE_DRAFT);
		entityManager.persist(expenseReport);
		addRequiredAuthorizations(expenseReport);
		entityManager.merge(expenseReport);
		return expenseReport;
	}

	// first, set the existing authorizations back to 'no response required.'
	protected void rescindResponseStatus(ExpenseReport expenseReport) {
		final Set<ExpenseReportAuthorization> authorizations = expenseReport.getExpenseReportAuthorizations();
		for (ExpenseReportAuthorization authorization : authorizations) {
			authorization.setRequiresResponse(false);
			entityManager.merge(authorization);
		}
	}

	@Transactional(readOnly = true)
	public ExpenseReportAuthorization getNextExpenseReportAuthorization(long expenseReportId) {

		ExpenseReport er = getExpenseReportById(expenseReportId);
		Set<ExpenseReportAuthorization> ers = er.getExpenseReportAuthorizations();
		for (ExpenseReportAuthorization era : ers) {
			if (era.isRequiresResponse()) {
				return era;
			}
		}

		return null;
	}

	/**
	 * traverses the authorization nodes, and sets the first non-authorized node to be 'active,' which is to say,
	 * it sets {@link ExpenseReportAuthorization#requiresResponse} to true
	 * This method should be used both when the report's first been submitted and any time anybody in the authorization
	 * chain approves it, and thus needs to send it up the chain for further approval, if required.
	 *
	 * @param expenseReportId the expense report
	 */
	protected void activateNextAuthorization(long expenseReportId) {
		final ExpenseReport expenseReport = getExpenseReportById(expenseReportId);

		if (expenseReport.getExpenseReportAuthorizations().size() == 0) {
			addRequiredAuthorizations(expenseReport);
		}

		rescindResponseStatus(expenseReport);

		final Set<ExpenseReportAuthorization> authorizations = expenseReport.getExpenseReportAuthorizations();

		// then find the first authorization that hasn't had a response (approved, rejected) and set it to 'require reponse'
		AuthorizationChainIterationCallback authorizationChainIterationCallback = new AuthorizationChainIterationCallback() {
			@Override
			public boolean ascendTheChainOfAuthorization(ExpenseHolder current, ExpenseHolder superior) {
				// ignore the root / current ExpenseHolder
				// at start of iteration, 'current' will be equal to the Eh that submitted report, which won't have an ExpenseReportAuthorization

				if (superior != null) {
					if (log.isDebugEnabled()) {
						log.debug("ascendTheChainOfAuthorization: current:" + current.getEmail() + ", superior: " + superior.getEmail());
					}
					for (ExpenseReportAuthorization expenseReportAuthorization : authorizations) {
						ExpenseHolder authorizingExpenseHolder = expenseReportAuthorization.getAuthorizingExpenseHolder();
						if (authorizingExpenseHolder.getExpenseHolderId() == superior.getExpenseHolderId()) {

							boolean hasBeenLookedAt = expenseReportAuthorization.isApproved() || expenseReportAuthorization.isRejected();

							if (!hasBeenLookedAt) {  // then we've found the first authorization that needs a response
								expenseReportAuthorization.setRequiresResponse(true);
								entityManager.merge(expenseReportAuthorization);
								return false;
							}
						}
					}

				}
				return true;
			}
		};
		ascendExpenseReportAuthorizationChain(expenseReport.getExpenseHolder(), authorizationChainIterationCallback);

	}

	@Transactional(readOnly = true)
	public ExpenseReportAuthorization getExpenseReportAuthorizationById(long expenseReportAuthorizationId) {
		return entityManager.find(ExpenseReportAuthorization.class, expenseReportAuthorizationId);
	}

	@Transactional(readOnly = true)
	public Collection<ExpenseReportLine> getExpenseReportLines(long expenseReportId) {
		ExpenseReport er = getExpenseReportById(expenseReportId);
		Set<ExpenseReportLine> lines = er.getExpenseReportLines();
		Hibernate.initialize(lines);
		return lines;
	}

	@Transactional(readOnly = true)
	public Collection<ExpenseReportAuthorization> getExpenseReportAuthorizationsForExpenseReport(long expenseReportId) {
		ExpenseReport expenseReport = getExpenseReportById(expenseReportId);
		return expenseReport.getExpenseReportAuthorizations();
	}

	@Transactional
	public void approveExpenseReportAuthorization(long expenseReportAuthorizationId, String feedback) {

		ExpenseReportAuthorization authorization = getExpenseReportAuthorizationById(expenseReportAuthorizationId);

		authorization.setApproved(true);
		authorization.setApprovedTime(new Date());
		authorization.setDescriptionOfResponse(feedback);
		entityManager.merge(authorization);

		activateNextAuthorization(authorization.getExpenseReport().getExpenseReportId());
	}

	@Transactional
	public void rejectExpenseReportAuthorization(long expenseReportAuthorizationId, String feedback) {

		ExpenseReportAuthorization authorization = getExpenseReportAuthorizationById(expenseReportAuthorizationId);
		authorization.setRejected(true);
		authorization.setRejectedTime(new Date());
		authorization.setDescriptionOfResponse(feedback);
		entityManager.merge(authorization);

		ExpenseReport er = authorization.getExpenseReport();
		rescindResponseStatus(er);
		er.setState(EXPENSE_REPORT_STATE_ERROR);
		entityManager.merge(er);

	}

	@Transactional
	public void ascendExpenseReportAuthorizationChain(long expenseHolderId, AuthorizationChainIterationCallback cb) {
		ExpenseHolder expenseHolder = expenseHolderService.getExpenseHolderById(expenseHolderId);
		ascendExpenseReportAuthorizationChain(expenseHolder, cb);
	}

	// convenience method for iteration
	protected void ascendExpenseReportAuthorizationChain(ExpenseHolder eh, AuthorizationChainIterationCallback cb) {
		ExpenseHolder expenseHolder = eh;
		ExpenseHolder superior;
		while ((superior = expenseHolder.getAuthorizingExpenseHolder()) != null) {
			boolean keepIterating = cb.ascendTheChainOfAuthorization(expenseHolder, superior);
			if (!keepIterating) {
				break;
			}
			expenseHolder = superior;
		}
	}

	@Transactional
	public void submitExpenseReportForApproval(long expenseReportId) {
		ExpenseReport expenseReport = getExpenseReportById(expenseReportId);
		expenseReport.setState(EXPENSE_REPORT_STATE_FINAL);
		entityManager.merge(expenseReport);
		activateNextAuthorization(expenseReportId);
	}

	@Transactional
	public ExpenseReport createExpenseReportFromChargeBatch(long expenseHolderId, long chargeBatchId) {
		ExpenseReport er = createExpenseReport(expenseHolderId);
		ChargeBatch chargeBatch = chargeBatchService.getChargeBatchById(chargeBatchId);
		for (Charge c : chargeBatch.getCharges()) {
			createExpenseReportLine(er.getExpenseReportId(), c.getChargeId());
		}
		return getExpenseReportById(er.getExpenseReportId());
	}

	protected void addRequiredAuthorizations(final ExpenseReport expenseReport) {
		AuthorizationChainIterationCallback chainIterationCallback = new AuthorizationChainIterationCallback() {
			@Override
			public boolean ascendTheChainOfAuthorization(ExpenseHolder current, ExpenseHolder superior) {
				ExpenseReportAuthorization authorization = new ExpenseReportAuthorization();
				authorization.setExpenseReport(expenseReport);
				authorization.setAuthorizingExpenseHolder(superior);
				authorization.setApproved(false);
				authorization.setRejected(false);
				authorization.setDescriptionOfResponse("");
				entityManager.persist(authorization);
				expenseReport.getExpenseReportAuthorizations().add(authorization);
				entityManager.merge(expenseReport);

				return true;
			}
		};
		ascendExpenseReportAuthorizationChain(expenseReport.getExpenseHolder(), chainIterationCallback);
	}

	@Transactional(readOnly = true)
	public ExpenseReport getExpenseReportById(long expenseReportId) {
		return entityManager.find(ExpenseReport.class, expenseReportId);
	}

	@Transactional
	public ExpenseReportLine createExpenseReportLine(long expenseReportId, long chargeId) {
		Charge charge = chargeBatchService.getChargeById(chargeId);
		ExpenseReport expenseReport = getExpenseReportById(expenseReportId);

		ExpenseReportLine expenseReportLine = new ExpenseReportLine();
		expenseReportLine.setCharge(charge);
		expenseReportLine.setExpenseReport(expenseReport);

		entityManager.persist(expenseReportLine);
		validateExpenseReportLine(expenseReportLine);
		entityManager.merge(expenseReportLine);
		return expenseReportLine;
	}


	protected void validateExpenseReportLine(ExpenseReportLine expenseReportLine) {
		ExpenseHolder expenseHolder = expenseReportLine.getExpenseReport().getExpenseHolder();
		double maxExpenditureAllowed = expenseHolder.getUnjustifiedChargeAmountThreshold();
		Charge charge = expenseReportLine.getCharge();
		if (charge.getChargeAmount() > maxExpenditureAllowed) {
			expenseReportLine.setRequiresReceipt(true);
		}
	}

	@Transactional(readOnly = true)
	public ExpenseReportLine getExpenseReportLineById(long erLid) {
		return entityManager.find(ExpenseReportLine.class, erLid);
	}

	@Transactional
	public Attachment createExpenseReportLineAttachment(long expenseReportLineId, long managedFileId) {

		ManagedFile managedFile = managedFileService.getManagedFileById(managedFileId);

		ExpenseReportLine erl = getExpenseReportLineById(expenseReportLineId);

		Attachment attachment = new Attachment();
		attachment.setExpenseReportLine(erl);
		attachment.setManagedFile(managedFile);

		entityManager.persist(attachment);

		return attachment;
	}


}
