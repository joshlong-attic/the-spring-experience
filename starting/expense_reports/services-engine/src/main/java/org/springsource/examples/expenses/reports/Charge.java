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

	public Charge() {
	}

	public Charge(boolean reconciled, long chargeId, double amount, String vendor) {
		this.reconciled = reconciled;
		this.chargeId = chargeId;
		this.amount = amount;
		this.vendor = vendor;
	}

	public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getChargeId() {
        return chargeId;
    }

    public void setChargeId(long chargeId) {
        this.chargeId = chargeId;
    }

    public boolean isReconciled() {
        return reconciled;
    }

    public void setReconciled(boolean reconciled) {
        this.reconciled = reconciled;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}