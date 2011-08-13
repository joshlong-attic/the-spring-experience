package org.springsource.examples.expenses.reports;

import org.springframework.util.Assert;
import org.springsource.examples.expenses.users.User;

/**
 * Another strategy: consult the {@link org.springsource.examples.expenses.users.User#expensableAmountWithoutReceipt} property
 *
 * @author Josh Long
 */
public class UserExpensableAmountLineItemValidationStrategy implements LineItemValidationStrategy {

	private User user;

	public UserExpensableAmountLineItemValidationStrategy(User user) {
		Assert.notNull(this.user, "the 'user' can't be null");
		this.user = user;
	}

	@Override
	public boolean lineItemRequiresReceipt(LineItem item) {
		double chargeAmt = item.getCharge().getChargeAmount();
		double allowedPerUserAmount = this.user.getExpensableAmountWithoutReceipt();
		return chargeAmt > allowedPerUserAmount;
	}
}
