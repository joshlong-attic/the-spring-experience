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

package org.springsource.html5expenses.reports.implementation;

/**
 * @author Josh Long
 */
public class DefaultExpenseValidationStrategy implements ExpenseValidationStrategy {

	private double maxAbsoluteValue;

	public DefaultExpenseValidationStrategy(double maxAbsoluteValue) {
		this.maxAbsoluteValue = maxAbsoluteValue;
	}

	public DefaultExpenseValidationStrategy() {
		this(25.0D);
	}

	@Override
	public boolean validate(Expense item) {
		if (item == null) {
			throw new IllegalArgumentException("the item can't be null");
		}
		double charge = item.getAmount();
		boolean receiptMissing = item.getReceiptFileId() == null;
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
		return !invalid;
	}
}
