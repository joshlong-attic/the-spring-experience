package org.springsource.examples.expenses.users;

import javax.persistence.*;

/**
 *
 * A placeholder entity to describe where the charges came from. Typically this is a {@link CreditCard} which
 * the user has used and is now liable for.
 *
 * @author Josh Long
 */
@Entity
public class CreditCard {
	private long creditCardId;
	private User user;

	public CreditCard() {
	}

	public CreditCard(long creditCardId, User user) {
		this.creditCardId = creditCardId;
		this.user = user;
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
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}