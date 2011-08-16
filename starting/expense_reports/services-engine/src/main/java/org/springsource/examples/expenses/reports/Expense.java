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