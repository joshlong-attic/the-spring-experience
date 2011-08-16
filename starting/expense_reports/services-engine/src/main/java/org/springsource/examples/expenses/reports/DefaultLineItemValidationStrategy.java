package org.springsource.examples.expenses.reports;

import org.springframework.util.Assert;

/**
 * @author Josh Long
 */
public class DefaultLineItemValidationStrategy implements LineItemValidationStrategy {

	private double maxAbsoluteValue = 25.0d;

    public void setMaxAbsoluteValue(double mav){
        Assert.isTrue(mav > 0 );
        this.maxAbsoluteValue = mav;
    }

	@Override
	public boolean lineItemRequiresReceipt(LineItem item) {
		Assert.notNull(item, "the 'lineItem' must not be null");
		double charge = item.getAmount();
		return (charge > maxAbsoluteValue);
	}
}
