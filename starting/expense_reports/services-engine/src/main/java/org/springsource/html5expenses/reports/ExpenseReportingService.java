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
	 * @param expenseId
	 * @param receiptBytes
	 * @return
	 */
	Long addReceipt(Long expenseId, byte[] receiptBytes );

	FilingResult fileReport(Long reportId);

	List<ExpenseReport> getOpenReports();

}
