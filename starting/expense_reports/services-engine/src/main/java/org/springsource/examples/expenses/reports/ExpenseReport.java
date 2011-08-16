package org.springsource.examples.expenses.reports;

import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * aggregate for reconciled charges, which are called line items.
 *
 * @author Josh Long
 */
public class ExpenseReport {

	/**
	 * the validation strategy
	 */
	private LineItemValidationStrategy lineItemValidationStrategy = new DefaultLineItemValidationStrategy();

	/**
	 * the id of this expense report
	 */
	private long id;

	/**
	 * the user ID
	 */
	private String userId;

	/**
	 * the state of the {@link ExpenseReport}
	 */
	private ExpenseReportState state;

	/**
	 * the line items for this expense report
	 */
	private Set<Expense> expenses = new HashSet<Expense>();

	/**
	 * which states can the report be in?
	 */
	public static enum ExpenseReportState {
		New, PendingReview, Closed
	}

	public ExpenseReport(String userId, ExpenseReportState state) {
		this.state = state;
		this.userId = userId;
		Assert.notNull(this.state, "the 'state' can't be null");
		Assert.notNull(this.userId, "the 'userId' can't be null");
	}

	public ExpenseReport(String userId) {
		this(userId, ExpenseReportState.New);
	}

	public long getId() {
		return id;
	}


	public Set<Expense> getExpenses() {
		return expenses;
	}

	public void setLineItemValidationStrategy(LineItemValidationStrategy lineItemValidationStrategy) {
		this.lineItemValidationStrategy = lineItemValidationStrategy;
	}

	public ExpenseReportState getState() {
		return state;
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
				boolean needsReceipt = lineItemValidationStrategy.lineItemRequiresReceipt(lineItem) && (lineItem.getAttachments().size() == 0);
				lineItem.setRequiresReceipt(needsReceipt);
				if (needsReceipt){
					valid = false;
				}
			}
			return valid;
		}
		*/
	public boolean isValid() {
		// todo fixme after refactoring
		return false;
		//    return validate();
	}

	public Expense createLineItem(Charge charge) {
		Expense item = new Expense(charge.getChargeId(), charge.getAmount());
		item.setCategory(charge.getVendor());
		getExpenses().add(item);
		return item;
	}


	public void fileReport() {
		if (!isValid()) {
			throw new IllegalStateException("the report's not valid and can't be filed.");
		}
		this.state = ExpenseReportState.PendingReview;
	}

	public void rejectReport() {
		this.state = ExpenseReportState.New;
	}

	public void closeReport() {
		if (!isValid()) {
			throw new IllegalStateException("the report's not valid and can't be filed.");
		}
		this.state = ExpenseReportState.Closed;
	}
}