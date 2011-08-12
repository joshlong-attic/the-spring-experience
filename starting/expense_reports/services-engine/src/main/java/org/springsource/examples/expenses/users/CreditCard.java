package org.springsource.examples.expenses.users;

import javax.persistence.*;

/**
 *
 * A placeholder entity to describe where the charges came from. Typically this is a {@link CreditCard} which
 * the user has used and is now liable for.
 *
 * @author Josh Long
 */

public class CreditCard {
	private long creditCardId;
	private User user;

	public CreditCard() {
	}

	public CreditCard(long creditCardId, User user) {
		this.creditCardId = creditCardId;
		this.user = user;
	}

	public long getCreditCardId() {
		return this.creditCardId;
	}

	public void setCreditCardId(long creditCardId) {
		this.creditCardId = creditCardId;
	}
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}