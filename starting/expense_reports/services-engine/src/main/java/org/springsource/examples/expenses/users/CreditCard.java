package org.springsource.examples.expenses.users;

/**
 * A placeholder entity to describe where the charges came from. Typically this is a {@link CreditCard} which
 * the user has used and is now liable for.
 *
 * @author Josh Long
 */

public class CreditCard {

	public CreditCard(User user, String cardLabel) {
		this.user = user;
		this.cardLabel = cardLabel;
	}

	private User user;

	private String cardLabel;

	public String getCardLabel() {
		return cardLabel;
	}

	public void setCardLabel(String cardLabel) {
		this.cardLabel = cardLabel;
	}

	public CreditCard() {
	}


	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}