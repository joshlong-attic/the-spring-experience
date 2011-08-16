package org.springsource.examples.expenses.reports;

import org.springsource.examples.expenses.fs.ManagedFile;

/**
 * A {@link LineItem} is basically an unjustified, unreconciled {@link Charge}.
 *
 * @author Josh Long
 */

public class LineItem {

	private long lineItemId;
	private ManagedFile receipt;
	private String category;
	private double amount;
	private long chargeId;

	public LineItem(long chargeId, double amount) {
		this.chargeId = chargeId;
		this.amount = amount;
	}

	public ManagedFile getReceipt() {
		return receipt;
	}

	public double getAmount() {
		return amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getChargeId() {
		return chargeId;
	}

	public long getLineItemId() {
		return lineItemId;
	}




}