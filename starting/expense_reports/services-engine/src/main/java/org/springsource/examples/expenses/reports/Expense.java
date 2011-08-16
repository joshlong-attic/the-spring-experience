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

import org.springsource.examples.expenses.fs.ManagedFile;

/**
 * A {@link Expense} is basically an unjustified, unreconciled {@link org.springsource.examples.expenses.charges.Charge}.
 *
 * @author Josh Long
 */

public class Expense {

	private String category;
	private double amount;
	private Long chargeId;
	private ManagedFile receipt;
	private boolean flagged;
	private String flag;

	public void flag(String mesg) {
		this.flagged = true;
		this.flag = mesg;
	}

	public void unflag() {
		this.flagged =false;
		this.flag = null ;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Expense(long chargeId, double amount) {
		this.chargeId = chargeId;
		this.amount = amount;
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

	public ManagedFile getReceipt() {
		return receipt;
	}

	public void setReceipt(ManagedFile receipt) {
		this.receipt = receipt;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}

	public String getFlag(){
	    return flag;
	}


}