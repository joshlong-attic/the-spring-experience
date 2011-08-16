package org.springsource.examples.expenses;


import junit.framework.Assert;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.springsource.examples.expenses.fs.ManagedFile;
import org.springsource.examples.expenses.reports.Charge;
import org.springsource.examples.expenses.reports.Expense;
import org.springsource.examples.expenses.reports.ExpenseReport;

import java.io.File;
import java.util.Set;

public class TestExpenseReport {

	private File desktop = new File(SystemUtils.getUserHome(), "Desktop");

	private Charge expensiveCharge = new Charge(32L, 232.00, "category 1");
	private Charge inexpensiveCharge = new Charge(33L, 22, "category 2");

	private ManagedFile coffeeReceipt = new ManagedFile(new File(new File(SystemUtils.getUserHome(), "Desktop"), "coffee.jpg"));

	@Test
	public void testOkLineItemReportValidation() throws Throwable {
		ExpenseReport expenseReport = new ExpenseReport();
		expenseReport.addExpense(inexpensiveCharge);
		Assert.assertTrue(expenseReport.validate());
	}

	@Test
	public void testEmptyReportValidation() throws Throwable {
		ExpenseReport expenseReport = new ExpenseReport();
		Assert.assertFalse(expenseReport.validate());
	}

	@Test
	public void testExpensiveExpenseValidation() throws Throwable {
		ExpenseReport expenseReport = new ExpenseReport();
		expenseReport.addExpense(expensiveCharge);
		Assert.assertFalse(expenseReport.validate());
	}

	@Test
	public void testExpensiveExpenseReconciliation() throws Throwable {
		ExpenseReport expenseReport = new ExpenseReport();
		Expense expense = expenseReport.addExpense(expensiveCharge);
		Assert.assertFalse(expenseReport.validate());
		expense.setReceipt(coffeeReceipt);
		Assert.assertTrue(expenseReport.validate());
	}

	@Test
	public void testRejection() throws Throwable {
		ExpenseReport expenseReport = new ExpenseReport();
		expenseReport.addExpense(inexpensiveCharge);
		Assert.assertTrue(expenseReport.validate());

		expenseReport.setPendingReview();
		Assert.assertTrue(expenseReport.getState().equals(ExpenseReport.ExpenseReportState.IN_REVIEW));

		// approver spots a bad expense
		Set<Expense> expenseSet = expenseReport.getExpenses();
		Expense expense = expenseSet.iterator().next();
		String error = "dude this is a receipt from your trip to the grocery store. Try again.";
		expense.flag(error);
		Assert.assertTrue(expense.isFlagged());
		Assert.assertTrue(expense.getErrorDescription().equals(error));
	}

	@Test
	public void testRejectionAndReconciliation () throws Throwable {

		ExpenseReport expenseReport = new ExpenseReport();
		expenseReport.addExpense(inexpensiveCharge);
		expenseReport.setPendingReview();
		Set<Expense> expenseSet = expenseReport.getExpenses();
		Expense expense = expenseSet.iterator().next();
		String error = "dude this is a receipt from your trip to the grocery store. Try again.";
		expense.flag(error);
		Assert.assertTrue(expense.isFlagged());
		Assert.assertTrue(expense.getErrorDescription().equals(error));


	}

}
