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
}