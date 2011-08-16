package org.springsource.examples.expenses;


import junit.framework.Assert;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.springsource.examples.expenses.fs.ManagedFile;
import org.springsource.examples.expenses.reports.Charge;
import org.springsource.examples.expenses.reports.Expense;
import org.springsource.examples.expenses.reports.ExpenseReport;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

public class TestExpenseReport {

	private File desktop = new File(SystemUtils.getUserHome(), "Desktop");

	private Charge expensiveCharge = new Charge(32L, 232.00, "category 1");
	private Charge inexpensiveCharge = new Charge(33L, 22, "category 2");
	private Collection<Charge> charges = Arrays.asList(expensiveCharge, inexpensiveCharge);

	private ManagedFile file = new ManagedFile(new File(new File(SystemUtils.getUserHome(), "Desktop"), "coffee.jpg"));

	@Test
	public void testValidation() throws Throwable {

		ExpenseReport expenseReport = new ExpenseReport();
		Assert.assertFalse(expenseReport.validate());
		expenseReport.addExpense(inexpensiveCharge);
		Assert.assertTrue(expenseReport.validate());
		Expense expensiveExpense = expenseReport.addExpense(expensiveCharge);
		Assert.assertFalse(expenseReport.validate());
		expensiveExpense.setReceipt(file);
		Assert.assertTrue(expenseReport.validate());
	}

	@Test
	public void testSubmission() throws Throwable {
	}
}
