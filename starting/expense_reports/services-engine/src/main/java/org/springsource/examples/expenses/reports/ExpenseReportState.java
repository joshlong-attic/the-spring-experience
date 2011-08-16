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
