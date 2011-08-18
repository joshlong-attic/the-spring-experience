/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springsource.html5expenses.reports;

import org.springsource.html5expenses.charges.Charge;

import java.util.List;

/**
 * manages expense reports
 *
 * @author Josh Long
 */
public interface ExpenseReportingService {

	/**
	 *
	 * Creates a new, {@link org.springsource.html5expenses.reports.implementation.ExpenseReportState#OPEN open} expense report with a purpose.
	 *
	 * @param purpose the purpose for this report, e.g., "Palo Alto Face to Face"
	 * @return the ID of the expense report
	 */
	Long createNewReport(String purpose);

	/**
	 * Retrieves all the charges that this user needs to account for.
	 *                                                                                                	 *
	 * @return All charges returned from this call can conceivably be added to a {@link ExpenseReport}
	 */
	List<Charge> getEligibleCharges();

	/**
	 *
	 * @param reportId   the ID of the report
	 * @param chargeIds the IDs of the charges to be added to the report
	 * @return a collection of the charges that were added to the report
	 */
	List<Expense> addExpenses(Long reportId, List<Long> chargeIds);

	/**
	 * Add a file that can be used to track the
	 * @param expenseId the ID of the expense
	 * @param receiptBytes the bytes representing the file
	 * @param originalFileName the original name of the file (so that the user can easily correlate)
	 * @return the ID of the newly created managed file
	 */
	Long addReceipt(Long expenseId, String originalFileName, byte[] receiptBytes );

	/**
	 * attempts to file a report.
	 *
	 * @param reportId the ID of the report to try to file
	 * @return a FilingResult is a wrapper that includes the confirmation status as well as an attached ExpenseReport which has validation flags that can be consulted in the event of a failure status
	 */
	FilingResult fileReport(Long reportId);

	/**
	 * returns all the {@link ExpenseReport expense reports} that have not be submitted and are thus not yet
	 * {@link org.springsource.html5expenses.reports.implementation.ExpenseReportState#CLOSED closed}
	 *
	 * @return the collection of ExpenseReports
	 */
	List<ExpenseReport> getOpenReports();

}
