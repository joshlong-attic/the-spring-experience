package org.springsource.examples.expenses.reports;

/**
 * @author Josh Long
 */
public interface ExpenseValidationStrategy {

	/**
	 * @param item the expenseReport to validate
	 * @return whether
	 */
	boolean validate(Expense item);


}
