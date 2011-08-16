package org.springsource.examples.expenses.reports;

import org.springsource.examples.expenses.fs.ManagedFile;

/**
 * A {@link LineItem} is basically an unjustified, unreconciled {@link Charge}.
 *
 * @author Josh Long
 */

public class LineItem {
	private long lineItemId;
	private long chargeId;
	private Attachment receipt;
	private String category;
	private double amount;


	public LineItem(ExpenseReport expenseReport, long chargeId, double amount) {
		this.chargeId = chargeId;
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setReceipt(Attachment attachment) {
		this.receipt = attachment;
	}

	public Attachment getReceipt() {
		return receipt;
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

	public void setChargeId(long chargeId) {
		this.chargeId = chargeId;
	}

	public long getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(long lineItemId) {
		this.lineItemId = lineItemId;
	}

	public void setReceipt(String description, ManagedFile file) {
		Attachment attachment = new Attachment(file);
		attachment.setDescription(description);
	}
}