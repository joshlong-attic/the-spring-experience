package org.springsource.examples.expenses.reports;

import java.util.HashSet;
import java.util.Set;

/**
 * aggregate for reconciled charges, which are called line items.
 *
 * @author Josh Long
 */
public class ExpenseReport {

	private ExpenseReportState state = ExpenseReportState.NEW;

	private Set<Expense> expenses = new HashSet<Expense>();

	private ExpenseValidationStrategy expenseValidationStrategy = new DefaultExpenseValidationStrategy();

	public static enum ExpenseReportState {
		NEW, PENDING_REVIEW, CLOSED
	}

	public Set<Expense> getExpenses() {
		return expenses;
	}

	public ExpenseReportState getState() {
		return state;
	}

	public ExpenseReport() {
	}

	public Expense addExpense(Charge charge) {
		Expense item = new Expense(charge.getId(), charge.getAmount());
		item.setCategory(charge.getCategory());
		getExpenses().add(item);
		return item;
	}

	public boolean validate() {
		boolean valid = true;
		for (Expense lineItem : getExpenses()) {
			if (!expenseValidationStrategy.validate(lineItem)) {
				valid = false;
			}
		}
		return valid;
	}

	public void setPendingReview() {
		if (!validate()) {
			throw new IllegalStateException("the report's not valid and can't be filed.");
		}
		this.state = ExpenseReportState.PENDING_REVIEW;
	}

	public void setNew() {
		this.state = ExpenseReportState.NEW;
	}

	public void setClosed() {
		if (!validate()) {
			throw new IllegalStateException("the report's not valid and can't be closed.");
		}
		this.state = ExpenseReportState.CLOSED;
	}
}