package org.springsource.examples.expenses.services;

import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.expenses.model.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Service charged with managing the approval, routing and creation of expense reports.
 *
 * @author Josh Long
 */
public class ExpenseReportService {

	@PersistenceContext private EntityManager entityManager;

	@Inject private ManagedFileService managedFileService;

	@Inject private ExpenseHolderService expenseHolderService;

	@Inject private ChargeBatchService chargeBatchService;

	@Transactional
	public ExpenseReport createExpenseReport(long expenseHolderId) {

		ExpenseHolder eh = expenseHolderService.getExpenseHolderById(expenseHolderId);

		ExpenseReport er = new ExpenseReport();
		er.setExpenseHolder(eh);

		entityManager.persist(er);
		return er;
	}


	@Transactional(readOnly = true)
	public ExpenseReport getExpenseReportById(long expenseReportId) {
		return entityManager.find(ExpenseReport.class, expenseReportId);
	}

	@Transactional
	public ExpenseReportLine createExpenseReportLine(long expenseReportId, long chargeId) {

		Charge charge = chargeBatchService.getChargeById(chargeId);
		ExpenseReport er = getExpenseReportById(expenseReportId);

		ExpenseReportLine erl = new ExpenseReportLine();
		erl.setCharge(charge);
		erl.setExpenseReport(er);

		entityManager.persist(erl);

		return erl;
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
