package org.springsource.examples.expenses.reports;

import org.springframework.util.Assert;
import org.springsource.examples.expenses.charges.Charge;
import org.springsource.examples.expenses.charges.ChargeBatch;
import org.springsource.examples.expenses.users.User;

import java.util.HashSet;
import java.util.Set;

/**
 * A collection of {@link LineItem}s, which in turn are merely reconciled, imported
 * {@link org.springsource.examples.expenses.charges.Charge charges} from
 * a {@link org.springsource.examples.expenses.charges.ChargeBatch charge batch}.
 *
 * @author Josh Long
 */

public class ExpenseReport {
	private long expenseReportId;
	private User user;
	private ExpenseReportState state;
	private Set<LineItem> lineItems = new HashSet<LineItem>(0);

	/**
	 * which states can the report be in?
	 */
	public static enum ExpenseReportState {
		FINAL, DRAFT
	}

	public ExpenseReport(User user, ExpenseReportState state) {
		this.user = user;
		this.state = state;
		Assert.notNull(this.state, "the 'state' can't be null");
	}

	public ExpenseReport(User usr) {
		this(usr, ExpenseReportState.DRAFT);
	}

	public long getExpenseReportId() {
		return this.expenseReportId;
	}

	public void setExpenseReportId(long expenseReportId) {
		this.expenseReportId = expenseReportId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void markAsFinal() {
		this.state = ExpenseReportState.FINAL;
	}

	public void markAsDraft() {
		this.state = ExpenseReportState.DRAFT;
	}

	public Set<LineItem> getLineItems() {
		return this.lineItems;
	}

	public void setLineItems(Set<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public static ExpenseReport buildExpenseReportFromChargeBatch(LineItemValidationStrategy lineItemValidationStrategy, User user, ChargeBatch batch) {
		ExpenseReport expenseReport = new ExpenseReport(user);
		for (Charge c : batch.getCharges()) {
			expenseReport.addLineItemFromCharge(lineItemValidationStrategy, c);
		}
		expenseReport.markAsDraft();
		return expenseReport;
	}

	public LineItem addLineItem(LineItemValidationStrategy lineItemValidationStrategy, LineItem li) {
		if (lineItemValidationStrategy.lineItemRequiresReceipt(li)) {
			li.setRequiresReceipt(true);
		}

		getLineItems().add(li);

		return li;
	}

	//todo i dont like the idea of this LineItemValidationStrategy
	// todo being passed around? maybe some sort of threadlocal holder?
	public LineItem addLineItemFromCharge(LineItemValidationStrategy lineItemValidationStrategy, Charge charge) {
		LineItem li = new LineItem();
		li.setCharge(charge);
		li.setExpenseReport(this);

		return addLineItem(lineItemValidationStrategy, li);
	}
}