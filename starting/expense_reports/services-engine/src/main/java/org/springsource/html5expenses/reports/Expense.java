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

/**
 *
 * A client view of the expense
 *
 * @see org.springsource.html5expenses.reports.implementation.Expense
 * @author Josh Long
 */
public class Expense {

	public Expense(String category, double amount, Long chargeId, Long receiptManagedFileId, boolean flagged, String flag) {
		this.category = category;
		this.amount = amount;
		this.chargeId = chargeId;
		this.receiptManagedFileId = receiptManagedFileId;
		this.flagged = flagged;
		this.flag = flag;
	}

	private String category;
	private double amount;
	private Long chargeId;
	private Long receiptManagedFileId;
	private boolean flagged;
	private String flag;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getChargeId() {
		return chargeId;
	}

	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}

	public Long getReceiptManagedFileId() {
		return receiptManagedFileId;
	}

	public void setReceiptManagedFileId(Long receiptManagedFileId) {
		this.receiptManagedFileId = receiptManagedFileId;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
