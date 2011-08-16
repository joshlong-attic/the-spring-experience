package org.springsource.examples.expenses.reports;

import java.util.Collection;

/**
 * manages expense reports - "just the contracts ma'am"
 *
 * @author Josh Long
 */
public interface ExpenseReportService {

	ExpenseReport createExpenseReport();

	void moveChargeToExpenseReport(Long chargeId, Long expenseReportId);

	void withdrawChargeFromExpenseReport(Long expenseReportId, Long chargeId);

	void addChargeToExpenseReport(Long chargeId, Long expenseReportId);

	Collection<ExpenseReport> getExpenseReportsForUser(String userId);

	Collection<ExpenseReport> getOpenExpenseReportsForUser(String userId);

	Collection<ExpenseReport> getClosedExpenseReportsForUser(String userId);

	/**
	 * presumably only the pool of 'admins' will call this method.
	 * don't want to introduce the concept of roles
	 */
	Collection<ExpenseReport> getExpenseReportsPendingReview();

	void flagExpense(Long expenseId, String reason);

	void unflagExpense(Long expenseId);

	ExpenseReport validateExpenseReport(Long expenseReportId);

	void submitExpenseReportForReview(Long expenseReporId);

	void closeExpenseReport(Long expenseReportId);

	void addChargesToExpenseReport(Long expenseReportId, Long... chargeIds);

	void categorizeExpense(Long expenseId, String category);

	void setReceiptForExpense( Long expenseId , Long managedFileId);

	ExpenseReport getExpenseReport( Long expenseReportId);
}
