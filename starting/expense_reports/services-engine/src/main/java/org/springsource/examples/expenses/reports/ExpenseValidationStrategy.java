package org.springsource.examples.expenses.reports;

/**
 *
 * Strategy interface. Arbitrates whether a given expense can be added to the system.
 *
 * @author Josh Long
 */
public interface ExpenseValidationStrategy {

	/**
	 * @param item the expenseReport to validate
	 * @return whether
	 */
	boolean validate(Expense item);


}
