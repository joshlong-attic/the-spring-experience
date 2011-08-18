/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springsource.examples.expenses;


import junit.framework.Assert;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.springsource.html5expenses.charges.Charge;
import org.springsource.html5expenses.files.ManagedFile;
import org.springsource.html5expenses.reports.implementation.Expense;
import org.springsource.html5expenses.reports.implementation.ExpenseReportState;

import java.io.File;
import java.util.Set;

public class TestExpenseReport {

	private Charge expensiveCharge = new Charge(32L, 232.00, "category 1");
	private Charge inexpensiveCharge = new Charge(33L, 22, "category 2");

	private ManagedFile coffeeReceipt = new ManagedFile(new File(new File(SystemUtils.getUserHome(), "Desktop"), "coffee.jpg"));

	@Test
	public void testOkLineItemReportValidation() throws Throwable {
		org.springsource.html5expenses.reports.implementation.ExpenseReport expenseReport = new org.springsource.html5expenses.reports.implementation.ExpenseReport();
		expenseReport.addExpense(inexpensiveCharge);
		Assert.assertTrue(expenseReport.validate());
	}

	@Test
	public void testEmptyReportValidation() throws Throwable {
		org.springsource.html5expenses.reports.implementation.ExpenseReport expenseReport = new org.springsource.html5expenses.reports.implementation.ExpenseReport();
		Assert.assertFalse(expenseReport.validate());
	}

	@Test
	public void testExpensiveExpenseValidation() throws Throwable {
		org.springsource.html5expenses.reports.implementation.ExpenseReport expenseReport = new org.springsource.html5expenses.reports.implementation.ExpenseReport();
		expenseReport.addExpense(expensiveCharge);
		Assert.assertFalse(expenseReport.validate());
	}

	@Test
	public void testExpensiveExpenseReconciliation() throws Throwable {
		org.springsource.html5expenses.reports.implementation.ExpenseReport expenseReport = new org.springsource.html5expenses.reports.implementation.ExpenseReport();
		Expense expense = expenseReport.addExpense(expensiveCharge);
		Assert.assertFalse(expenseReport.validate());
		expense.setReceipt(coffeeReceipt);
		Assert.assertTrue(expenseReport.validate());
	}

	@Test
	public void testRejection() throws Throwable {
		org.springsource.html5expenses.reports.implementation.ExpenseReport expenseReport = new org.springsource.html5expenses.reports.implementation.ExpenseReport();
		expenseReport.addExpense(inexpensiveCharge);
		Assert.assertTrue(expenseReport.validate());

		expenseReport.setPendingReview();
		Assert.assertTrue(expenseReport.getState().equals( ExpenseReportState.IN_REVIEW));

		// approver spots a bad expense
		Set<org.springsource.html5expenses.reports.implementation.Expense> expenseSet = expenseReport.getExpenses();
		org.springsource.html5expenses.reports.implementation.Expense expense = expenseSet.iterator().next();
		String error = "dude this is a receipt from your trip to the grocery store. Try again.";
		expense.flag(error);
		Assert.assertTrue(expense.isFlagged());
		Assert.assertTrue(expense.getFlag().equals(error));
	}

	@Test
	public void testRejectionAndReconciliation() throws Throwable {

		org.springsource.html5expenses.reports.implementation.ExpenseReport expenseReport = new org.springsource.html5expenses.reports.implementation.ExpenseReport();
		expenseReport.addExpense(inexpensiveCharge);
		expenseReport.setPendingReview();
		Set<org.springsource.html5expenses.reports.implementation.Expense> expenseSet = expenseReport.getExpenses();
		org.springsource.html5expenses.reports.implementation.Expense expense = expenseSet.iterator().next();
		String error = "dude this is a receipt from your trip to the grocery store. Try again.";
		expense.flag(error);
		Assert.assertTrue(expense.isFlagged());
		Assert.assertTrue(expense.getFlag().equals(error));
	}

	@Test
	public void testFailedSubmission() throws Throwable {
		org.springsource.html5expenses.reports.implementation.ExpenseReport expenseReport = new org.springsource.html5expenses.reports.implementation.ExpenseReport();
		expenseReport.addExpense(inexpensiveCharge);
		expenseReport.setPendingReview();
		Set<org.springsource.html5expenses.reports.implementation.Expense> expenseSet = expenseReport.getExpenses();
		org.springsource.html5expenses.reports.implementation.Expense expense = expenseSet.iterator().next();
		String error = "dude this is a receipt from your trip to the grocery store. Try again.";
		expense.flag(error);
		Assert.assertTrue(expense.isFlagged());
		Assert.assertTrue(expense.getFlag().equals(error));
		try {
			expenseReport.setPendingReview();
			Assert.fail("it should not be possible to submit an invalid expense report to anything but 'open'");
		} catch (Throwable th) {
		    // noop
		}
	}

	@Test
	public void testFailedClose() throws Throwable {
		org.springsource.html5expenses.reports.implementation.ExpenseReport expenseReport = new org.springsource.html5expenses.reports.implementation.ExpenseReport();
		expenseReport.addExpense(inexpensiveCharge);
		expenseReport.setPendingReview();
		Assert.assertTrue(expenseReport.getState().equals(ExpenseReportState.IN_REVIEW))  ;
		expenseReport.getExpenses().iterator().next().flag("No! You can't use the company card for *THAT*");
		Assert.assertTrue(expenseReport.isFlagged());
		try {
			expenseReport.setClosed();
			Assert.fail("you shouldn't be able to close a flagged expense report"); }
		catch (Throwable t ){
		//	System.err.println("exception occurred " + ExceptionUtils.getFullStackTrace(t));
		}
		ExpenseReportState state = expenseReport.getState();
		Assert.assertTrue(state.equals(ExpenseReportState.IN_REVIEW))  ;
	}

	@Test
	public void testFlagMutation () throws Throwable {
		org.springsource.html5expenses.reports.implementation.ExpenseReport expenseReport = new org.springsource.html5expenses.reports.implementation.ExpenseReport();
		expenseReport.addExpense(inexpensiveCharge);
		expenseReport.setPendingReview();
		Assert.assertTrue(expenseReport.getState().equals(ExpenseReportState.IN_REVIEW))  ;
		org.springsource.html5expenses.reports.implementation.Expense expense = expenseReport.getExpenses().iterator().next()  ;
		expense.flag("No! You can't use the company card for *THAT*");
		Assert.assertTrue(expenseReport.isFlagged());
		expense.unflag();
		Assert.assertTrue(!expenseReport.isFlagged());
	}

	@Test
	public void testSuccessfulSubmission() throws Throwable {
		org.springsource.html5expenses.reports.implementation.ExpenseReport expenseReport = new org.springsource.html5expenses.reports.implementation.ExpenseReport();
		expenseReport.addExpense(inexpensiveCharge);
		expenseReport.setPendingReview();

		expenseReport.setClosed();
		Assert.assertTrue(expenseReport.getState().equals(ExpenseReportState.CLOSED))  ;
	}
}
