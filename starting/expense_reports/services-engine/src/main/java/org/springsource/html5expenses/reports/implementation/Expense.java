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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * A {@link Expense} is basically an unjustified, unreconciled {@link org.springsource.html5expenses.charges.implementation.Charge}.
 *
 * @author Josh Long
 */

@Entity
public class Expense {

	/**
	 * default ctor for Hibernate
	 */
	Expense() {
	}

	public Expense(long chargeId, double amount) {
		this.chargeId = chargeId;
		this.amount = amount;
	}

	@ManyToOne
	private ExpenseReport expenseReport;

	@Id @GeneratedValue
	private Long id;

	private String category;
	private double amount;
	private Long chargeId;
	private Long receiptFileId;
	private boolean flagged;
	private String flag;

	public void flag(String mesg) {
		this.flagged = true;
		this.flag = mesg;
	}

	public void unflag() {
		this.flagged = false;
		this.flag = null;
	}

	public Long getReceiptFileId() {
		return receiptFileId;
	}

	public void setReceiptFileId(Long receiptFileId) {
		this.receiptFileId = receiptFileId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public double getAmount() {
		return amount;
	}

	public Long getChargeId() {
		return chargeId;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExpenseReport getExpenseReport() {
		return expenseReport;
	}

	public void setExpenseReport(ExpenseReport expenseReport) {
		this.expenseReport = expenseReport;
	}


}