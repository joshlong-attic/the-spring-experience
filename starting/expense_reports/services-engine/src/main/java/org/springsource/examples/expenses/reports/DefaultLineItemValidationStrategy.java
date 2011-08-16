package org.springsource.examples.expenses.reports;

import org.springframework.util.Assert;

/**
 * @author Josh Long
 */
public class DefaultLineItemValidationStrategy implements LineItemValidationStrategy {

	private double maxAbsoluteValue ;

	public DefaultLineItemValidationStrategy(double maxAbsoluteValue) {
		this.maxAbsoluteValue = maxAbsoluteValue;
	}

	public DefaultLineItemValidationStrategy( ) {
		this(25.0d);
	}

	@Override
	public boolean validate(Expense item) {
		Assert.notNull(item, "the 'lineItem' must not be null");
		double charge = item.getAmount();
		return (charge > maxAbsoluteValue);
	}
}
