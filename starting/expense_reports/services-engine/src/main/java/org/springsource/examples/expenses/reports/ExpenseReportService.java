package org.springsource.examples.expenses.reports;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.expenses.charges.Charge;
import org.springsource.examples.expenses.charges.ChargeBatch;
import org.springsource.examples.expenses.charges.ChargeBatchService;
import org.springsource.examples.expenses.fs.ManagedFile;
import org.springsource.examples.expenses.fs.ManagedFileService;
import org.springsource.examples.expenses.users.ExpenseHolder;
import org.springsource.examples.expenses.users.ExpenseHolderService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Service charged with managing the approval, routing and creation of expense reports.
 *
 * @author Josh Long
 */
@Service
public class ExpenseReportService {

	/**
	 * behind the scenes, the code will store Strings, but we'll surface an enum as part of the surface API
	 */
	public static enum ExpenseReportState {
		DRAFT, FINAL, ERROR
	}


	// well known states for an ExpenseReport to be in
	@PersistenceContext private EntityManager entityManager;

	@Inject private ManagedFileService managedFileService;

	@Inject private ExpenseHolderService expenseHolderService;

	@Inject private ChargeBatchService chargeBatchService;

	@Transactional
	public ExpenseReport createExpenseReport(long expenseHolderId) {
		ExpenseHolder eh = expenseHolderService.getExpenseHolderById(expenseHolderId);
		ExpenseReport expenseReport = new ExpenseReport();
		expenseReport.setExpenseHolder(eh);
		expenseReport.setState(ExpenseReportState.DRAFT.name());
		entityManager.persist(expenseReport);
		entityManager.merge(expenseReport);
		return expenseReport;
	}


	@Transactional(readOnly = true)
	public Collection<ExpenseReportLine> getExpenseReportLines(long expenseReportId) {
		ExpenseReport er = getExpenseReportById(expenseReportId);

		Set<ExpenseReportLine> lines = er.getExpenseReportLines();

		List<ExpenseReportLine> linesList = new ArrayList<ExpenseReportLine>();

		for (ExpenseReportLine erl : lines) {
			Hibernate.initialize(erl.getCharge());
			linesList.add(erl);
		}


		return linesList;
	}

	@Transactional
	public void submitExpenseReportForApproval(long expenseReportId) {
		ExpenseReport expenseReport = getExpenseReportById(expenseReportId);
		expenseReport.setState(ExpenseReportState.FINAL.name());
		entityManager.merge(expenseReport);
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
	public Attachment createExpenseReportLineAttachment(long expenseReportLineId, long managedFileId, String description) {

		ManagedFile managedFile = managedFileService.getManagedFileById(managedFileId);

		ExpenseReportLine erl = getExpenseReportLineById(expenseReportLineId);

		Attachment attachment = new Attachment();
		attachment.setExpenseReportLine(erl);
		attachment.setDescription(description);
		attachment.setManagedFile(managedFile);

		entityManager.persist(attachment);

		return attachment;
	}


}
