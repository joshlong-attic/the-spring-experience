package org.springsource.examples.expenses.reports;

import org.springsource.examples.expenses.users.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * A collection of {@link LineItem}s, which in turn are merely reconciled, imported
 * {@link org.springsource.examples.expenses.charges.Charge charges} from
 * a {@link org.springsource.examples.expenses.charges.ChargeBatch charge batch}.
 *
 * @author Josh Long
 */

public class ExpenseReport {
	private long expenseReportId;
	private User user;
	private String state;
	private Set<LineItem> lineItems = new HashSet<LineItem>(0);

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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<LineItem> getLineItems() {
		return this.lineItems;
	}

	public void setLineItems(Set<LineItem> lineItems) {
		this.lineItems = lineItems;
	}
}