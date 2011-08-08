package org.springsource.examples.expenses.user;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Josh Long
 */
@Entity
@Table(name = "credit_card", schema = "public")
public class CreditCard implements java.io.Serializable {
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
	@Column(name = "credit_card_id", unique = true, nullable = false)
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