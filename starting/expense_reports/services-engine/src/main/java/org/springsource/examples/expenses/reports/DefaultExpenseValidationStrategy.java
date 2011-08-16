package org.springsource.examples.expenses.reports;

/**
 * @author Josh Long
 */
public class DefaultExpenseValidationStrategy implements ExpenseValidationStrategy {

	private double maxAbsoluteValue;

	public DefaultExpenseValidationStrategy(double maxAbsoluteValue) {
		this.maxAbsoluteValue = maxAbsoluteValue;
	}

	public DefaultExpenseValidationStrategy() {
		this(25.0d);
	}

	@Override
	public boolean validate(Expense item) {
		if (item == null) {
			throw new IllegalArgumentException("the item can't be null");
		}
		double charge = item.getAmount();
		boolean receiptMissing = item.getReceipt() == null;
		boolean requiresReceipt = charge > maxAbsoluteValue;
		boolean invalid = false;

		if (requiresReceipt && receiptMissing) {
			item.flag("Receipt required");
			invalid = true;
		}

		if (requiresReceipt && !receiptMissing) {
			invalid = false;
		}

		if (!requiresReceipt) {
			invalid = false;
		}

		boolean valid = !invalid;
		if (valid) {
			item.unflag();
		}
		return valid;
	}
}
