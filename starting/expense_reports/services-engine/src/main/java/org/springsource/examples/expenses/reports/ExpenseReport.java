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
		NEW, PENDING_RESOLUTION, CLOSED
	}

	public Set<Expense> getExpenses() {
		return expenses;
	}

	public ExpenseReportState getState() {
		return state;
	}

	public ExpenseReport() {
	}


	/**
	 * reassess the state of this entity
	 *
	 * @return is the {@code ExpenseReport} in a known state.
	 */
	/* todo fixme after refactoring!

		protected boolean validate() {
			boolean valid = true;
			for (Expense lineItem : getExpenses()) {
				boolean needsReceipt = expenseValidationStrategy.lineItemRequiresReceipt(lineItem) && (lineItem.getAttachments().size() == 0);
				lineItem.setRequiresReceipt(needsReceipt);
				if (needsReceipt){
					valid = false;
				}
			}
			return valid;
		}
		*/
	public boolean validate() {
		// todo fixme after refactoring
		return false;
		//    return validate();
	}

	public Expense addExpense(Charge charge) {
		Expense item = new Expense( charge.getId() , charge.getAmount());
		item.setCategory(charge.getCategory());
		getExpenses().add(item);
		return item;
	}


	public void fileReport() {
		if (!validate()  ) {
			throw new IllegalStateException("the report's not valid and can't be filed.");
		}
		this.state = ExpenseReportState.PENDING_RESOLUTION;
	}

	public void rejectReport() {
		this.state = ExpenseReportState.NEW;
	}

	public void closeReport() {
		if (!validate()  ) {
			throw new IllegalStateException("the report's not valid and can't be closed.");
		}
		this.state = ExpenseReportState.CLOSED;
	}
}