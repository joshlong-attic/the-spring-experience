package org.springsource.html5expenses.reports.implementation;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.html5expenses.charges.Charge;
import org.springsource.html5expenses.charges.ChargeService;
import org.springsource.html5expenses.files.ManagedFile;
import org.springsource.html5expenses.files.ManagedFileService;
import org.springsource.html5expenses.reports.ExpenseReportingService;
import org.springsource.html5expenses.reports.FilingResult;
import org.springsource.html5expenses.reports.FilingResultStatus;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.util.*;

@Service
public class DatabaseExpenseReportingService implements ExpenseReportingService {

	private String openExpenseReportsQL = String.format("SELECT er FROM %s er WHERE er.state = :state ", ExpenseReport.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@Inject private ManagedFileService fileService;

	@Inject private ChargeService chargeService;

	@Transactional
	public Long createNewReport(String purpose) {
		ExpenseReport expenseReport = new ExpenseReport();
		expenseReport.setOpen();
		expenseReport.setPurpose(purpose);
		entityManager.persist(expenseReport);
		return expenseReport.getId();
	}

	@Transactional
	public List<Charge> getEligibleCharges() {
		return this.chargeService.getEligibleCharges();
	}

	@Transactional
	public List<org.springsource.html5expenses.reports.Expense> addExpenses(Long reportId, List<Long> chargeIds) {
		org.springsource.html5expenses.reports.implementation.ExpenseReport er =
				entityManager.find(org.springsource.html5expenses.reports.implementation.ExpenseReport.class, reportId);
		for (Long chargeId : chargeIds) {
			Charge c = chargeService.getCharge(chargeId);
			Expense expense  = er.addExpense(c);
		   	entityManager.persist(expense);
		}
		entityManager.merge(er);
		return new ArrayList<org.springsource.html5expenses.reports.Expense>( buildExpensesFrom( er.getExpenses()) );
	}

	@Transactional
	public Long addReceipt(Long expenseId, String originalFileName, byte[] receiptBytes) {
		ManagedFile file = fileService.createManagedFile(receiptBytes.length, originalFileName);
		String pathToManagedFile = fileService.getLocalPathForManagedFile(file.getId());
		OutputStream os = null;
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(receiptBytes);
			os = new FileOutputStream(new File(pathToManagedFile));
			IOUtils.copy(is, os);
		} catch (IOException e) {
			throw new RuntimeException("an error occurred when trying to write " +
					                   "the file to the file system", e);
		} finally {
			if (os != null) {
				IOUtils.closeQuietly(os);
			}
			if (is != null) {
				IOUtils.closeQuietly(is);
			}
		}
		return file.getId();
	}

	@Transactional
	public FilingResult fileReport(Long reportId) {
		ExpenseReport er = entityManager.find(ExpenseReport.class, reportId);
		boolean validationSucceeded = er.validate();
		if (validationSucceeded) {
			er.setPendingReview();
			entityManager.merge(er);
		}
		return new FilingResult(buildExpenseReportFrom(er), validationSucceeded ? FilingResultStatus.OK : FilingResultStatus.VALIDATION_ERROR);
	}

	@Transactional
	public List<org.springsource.html5expenses.reports.ExpenseReport> getOpenReports() {
		Collection<ExpenseReport> ers = entityManager.createQuery(openExpenseReportsQL, ExpenseReport.class)
				                                .setParameter("state", ExpenseReportState.OPEN)
				                                .getResultList();
		return new ArrayList<org.springsource.html5expenses.reports.ExpenseReport>(buildExpenseReportsFrom(new HashSet<ExpenseReport>(ers)));
	}


	private org.springsource.html5expenses.reports.Expense buildExpenseFrom(org.springsource.html5expenses.reports.implementation.Expense ex) {
		return new org.springsource.html5expenses.reports.Expense(ex.getCategory(), ex.getAmount(), ex.getChargeId(), ex.getReceiptFileId(), ex.isFlagged(), ex.getFlag());
	}

	private Set<org.springsource.html5expenses.reports.ExpenseReport> buildExpenseReportsFrom(Set<ExpenseReport> ers) {
		Set<org.springsource.html5expenses.reports.ExpenseReport> returnSet = new HashSet<org.springsource.html5expenses.reports.ExpenseReport>();

		for (ExpenseReport er : ers) {
			returnSet.add(buildExpenseReportFrom(er));
		}

		return returnSet;
	}

	private Set<org.springsource.html5expenses.reports.Expense> buildExpensesFrom(Set<org.springsource.html5expenses.reports.implementation.Expense> expenses) {
		Set<org.springsource.html5expenses.reports.Expense> result = new HashSet<org.springsource.html5expenses.reports.Expense>();
		for (org.springsource.html5expenses.reports.implementation.Expense e : expenses) {
			result.add(buildExpenseFrom(e));
		}
		return result;
	}

	private org.springsource.html5expenses.reports.ExpenseReport buildExpenseReportFrom(org.springsource.html5expenses.reports.implementation.ExpenseReport expenseReport) {
		org.springsource.html5expenses.reports.ExpenseReport e = new org.springsource.html5expenses.reports.ExpenseReport();
		e.setId(expenseReport.getId());
		e.setPurpose(expenseReport.getPurpose());
		e.setExpenses(buildExpensesFrom(expenseReport.getExpenses()));
		return e;
	}

}
