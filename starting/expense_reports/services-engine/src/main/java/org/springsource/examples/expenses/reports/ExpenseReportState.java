package org.springsource.examples.expenses.reports;

/**
 * States for the expense report.
 *
 * There are well known transitions:
 *
 *  From OPEN => IN_REVIEW
 *  From IN_REVIEW => OPEN (where somebody must revisit the report and correct any faults)
 *  From IN_REVIEW => CLOSED (everything's OK, and everything's justified.)
 *
 * @author Josh Long
 */
public enum ExpenseReportState {
	/**
	 * the default state of the {@link ExpenseReport}.
	 * Also, when a report's rejected, it returns to this state.
	 */
	OPEN,

	/**
	 * when a person authorized to approve the report is looking
	 * at it, it's "under review," which this flag describes
	 */
	IN_REVIEW,

	/**
	 * when a report's been successfully submitted and approved, it can finally be closed.
	 * Once closed, business continues as normal and the expenses will be paid.
	 */
	CLOSED
}
