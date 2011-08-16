package org.springsource.examples.expenses.reports;

import org.springsource.examples.expenses.fs.ManagedFile;

/**
 * A {@link Expense} is basically an unjustified, unreconciled {@link Charge}.
 *
 * @author Josh Long
 */

public class Expense {

	private String category;
	private double amount;
	private Long chargeId;
	private ManagedFile receipt;
	private boolean error;
	private String errorDescription;

	public void flag(String mesg) {
		this.error = true;
		this.errorDescription = mesg;
	}

	public void unflag() {
		this.error =false;
		this.errorDescription = null ;
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

	public boolean isInError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}