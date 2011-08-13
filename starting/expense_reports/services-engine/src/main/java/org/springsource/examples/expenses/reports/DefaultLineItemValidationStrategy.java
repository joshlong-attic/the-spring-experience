package org.springsource.examples.expenses.reports;

import org.springframework.util.Assert;

public class DefaultLineItemValidationStrategy implements LineItemValidationStrategy {

	private double maxAbsoluteValue = 25.0d;

	@Override
	public boolean lineItemRequiresReceipt(LineItem item) {
		Assert.notNull(item, "the 'lineItem' must not be null");
		double charge = item.getCharge().getChargeAmount();
		return (charge > maxAbsoluteValue);
	}
}
