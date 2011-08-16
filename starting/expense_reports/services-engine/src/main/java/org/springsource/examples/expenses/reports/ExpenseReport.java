package org.springsource.examples.expenses.reports;

import java.util.HashSet;
import java.util.Set;

/**
 * aggregate for reconciled charges, which are called line items.
 *
 * @author Josh Long
 */
public class ExpenseReport {

	private ExpenseReportState state = ExpenseReportState.OPEN;

	private Set<Expense> expenses = new HashSet<Expense>();

	private ExpenseValidationStrategy expenseValidationStrategy = new DefaultExpenseValidationStrategy();

	public Set<Expense> getExpenses() {
		return expenses;
	}

	public ExpenseReportState getState() {
		return state;
	}

	public Expense addExpense(Charge charge) {

		if (!this.state.equals(ExpenseReportState.OPEN)) {
			throw new IllegalStateException("you can't add expenses to a closed expense report.");
		}

		Expense item = new Expense(charge.getId(), charge.getAmount());
		item.setCategory(charge.getCategory());
		getExpenses().add(item);
		return item;
	}

	public boolean validate() {
		boolean valid = getExpenses().size() > 0;
		for (Expense expense : getExpenses()) {
			if (!expenseValidationStrategy.validate(expense)) {
				valid = false;
				// todo introduce WELL_KNOWN_RECEIPT_REQUIRED constant or something?
			}
		}
		return valid;
	}

	public void setPendingReview() {
		if (!validate()) {
			throw new IllegalStateException("the report's not valid and can't be filed.");
		}
		this.state = ExpenseReportState.IN_REVIEW;
	}

	public void setOpen() {
		this.state = ExpenseReportState.OPEN;
	}

	public void setClosed() {
		if (!validate()) {
			throw new IllegalStateException("the report's not valid and can't be closed.");
		}
		this.state = ExpenseReportState.CLOSED;
	}


}