package org.springsource.examples.expenses.reports;


/**
 *  a {@link Charge} is a free-standing entity that represents the boundry between an external credit card system and the
 *  expense report system.  Essentially, charges may be selected and then added into {@link LineItem}s.
 * @author Josh Long
 */
public class Charge {
    private boolean reconciled ;
	private long chargeId;
	private double amount;
	private String vendor ;

	public boolean isReconciled() {
		return reconciled;
	}

	public long getChargeId() {
		return chargeId;
	}

	public double getAmount() {
		return amount;
	}

	public String getVendor() {
		return vendor;
	}

	public Charge(boolean reconciled, long chargeId, double amount, String vendor) {
		this.reconciled = reconciled;
		this.chargeId = chargeId;
		this.amount = amount;
		this.vendor = vendor;
	}
 
}