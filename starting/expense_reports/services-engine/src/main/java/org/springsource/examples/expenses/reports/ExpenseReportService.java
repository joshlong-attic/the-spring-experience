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

package org.springsource.examples.expenses.reports;

import java.util.Collection;

/**
 * manages expense reports - "just the contracts ma'am"
 *
 * @author Josh Long
 */
public interface ExpenseReportService {

	ExpenseReport createExpenseReport();

	void moveChargeToExpenseReport(Long chargeId, Long expenseReportId);

	void withdrawChargeFromExpenseReport(Long expenseReportId, Long chargeId);

	void addChargeToExpenseReport(Long chargeId, Long expenseReportId);

	Collection<ExpenseReport> getExpenseReportsForUser(String userId);

	Collection<ExpenseReport> getOpenExpenseReportsForUser(String userId);

	Collection<ExpenseReport> getClosedExpenseReportsForUser(String userId);

	/**
	 * presumably only the pool of 'admins' will call this method.
	 * don't want to introduce the concept of roles
	 */
	Collection<ExpenseReport> getExpenseReportsPendingReview();

	void flagExpense(Long expenseId, String reason);

	void unflagExpense(Long expenseId);

	ExpenseReport validateExpenseReport(Long expenseReportId);

	void submitExpenseReportForReview(Long expenseReporId);

	void closeExpenseReport(Long expenseReportId);

	void addChargesToExpenseReport(Long expenseReportId, Long... chargeIds);

	void categorizeExpense(Long expenseId, String category);

	void setReceiptForExpense( Long expenseId , Long managedFileId);

	ExpenseReport getExpenseReport( Long expenseReportId);
}
