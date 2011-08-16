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
	public void testSubmittingExpenseReport() throws Throwable {

		ExpenseReport expenseReport = new ExpenseReport();
		Assert.assertFalse( expenseReport.validate());
		expenseReport.addExpense(inexpensiveCharge);
		Assert.assertTrue(expenseReport.validate());
		Expense expensiveExpense = expenseReport.addExpense(expensiveCharge);
		Assert.assertFalse(expenseReport.validate());
		expensiveExpense.setReceipt(file);
		Assert.assertTrue(expenseReport.validate());
	}

	/* private File desktop = new File(SystemUtils.getUserHome(), "Desktop");

		@Test
		public void testSubmittingExpenseReport() throws Throwable {

			String[] users = "jlong,kdonald".split(",");

			long counter = 0;

			for (String userId : users) {
				ExpenseReport expenseReport = new ExpenseReport(userId);
				Assert.assertTrue("new reports are always 'OPEN'", expenseReport.getState().equals(ExpenseReport.ExpenseReportState.OPEN));
				for (int i = 0; i < 5; i++) {
					expenseReport.addLineItemFromCharge(++counter, (Math.random() * 50));
				}
				expenseReport.addLineItemFromCharge(++counter, 26); // to make sure one fails

				Assert.assertTrue("the expense report should be invalid", !expenseReport.isValid());

				int i = 0;
				for (Expense lineItem : expenseReport.getExpenses()) {
					i += 1;
					*//*if (lineItem.isRequiresReceipt()) {
                    ManagedFile receipt = new ManagedFile(new File(desktop, i + "_receipt.jpg"));
                  //  Receipt attachment = lineItem.addAttachment("this is the receipt for the hotel (#" + i + ") I stayed in ", receipt);
                    Assert.assertNotNull("the attachment can't be null", attachment);
                }*//*
            }
            Assert.assertTrue("after corrections, the report should be valid now", expenseReport.isValid());
            expenseReport.setPendingReview();
            Assert.assertTrue(expenseReport.isValid());
        }
    }


    @Test
    public void testCreatingAFaultyExpenseReport() throws Throwable {

        String[] users = "jlong,kdonald".split(",");

	    long counter = 0;

        for (String userId : users) {
            ExpenseReport expenseReport = new ExpenseReport(userId);
            Assert.assertTrue("new reports are always 'OPEN'", expenseReport.getState().equals(ExpenseReport.ExpenseReportState.OPEN));
            for (int i = 0; i < 5; i++) {
                expenseReport.addLineItemFromCharge(++counter, (Math.random() * 50));
            }
            expenseReport.addLineItemFromCharge(++counter, 26); // to make sure one fails

            Assert.assertTrue("the expense report should be invalid", !expenseReport.isValid());
            try {
                expenseReport.setPendingReview();
                Assert.fail("we should never be able to file an invalid report");
            } catch (Throwable th) {
                // noop
            }

            Assert.assertTrue("the expense report's no longer valid", !expenseReport.isValid());

            *//*Expense li = (Expense) CollectionUtils.find(expenseReport.getExpenses(), new Predicate() {
                @Override
                public boolean evaluate(Object object) {
                    Expense li = (Expense) object;
                    return li.isRequiresReceipt();
                }
            });

            Assert.assertTrue(li != null && li.isRequiresReceipt());*//*

        }
    }*/
}
