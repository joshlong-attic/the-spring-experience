package org.springsource.examples.expenses.users;

import javax.persistence.*;

/**
 * @author Josh Long
 */
@Entity
public class CreditCard {
	private long creditCardId;
	private ExpenseHolder expenseHolder;

	public CreditCard() {
	}

	public CreditCard(long creditCardId, ExpenseHolder expenseHolder) {
		this.creditCardId = creditCardId;
		this.expenseHolder = expenseHolder;
	}

	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	public long getCreditCardId() {
		return this.creditCardId;
	}

	public void setCreditCardId(long creditCardId) {
		this.creditCardId = creditCardId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_holder_id", nullable = false)
	public ExpenseHolder getExpenseHolder() {
		return this.expenseHolder;
	}

	public void setExpenseHolder(ExpenseHolder expenseHolder) {
		this.expenseHolder = expenseHolder;
	}
}