package org.springsource.examples.expenses.charges;

import org.springsource.examples.expenses.reports.LineItem;
import org.springsource.examples.expenses.users.CreditCard;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link Charge} is a non reconciled charge that has come from some sort of credit card source. (The placeholder entity
 * to represent that source is {@link org.springsource.examples.expenses.users.CreditCard}.
 *
 * @author Josh Long
 */
public class Charge {
	private long chargeId;
	private ChargeBatch chargeBatch;
	private double chargeAmount;
	private String description;
	private CreditCard creditCard;
	private Set<LineItem> lineItems = new HashSet<LineItem>(0);

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {

		this.creditCard = creditCard;
	}


	public long getChargeId() {
		return this.chargeId;
	}

	public void setChargeId(long chargeId) {
		this.chargeId = chargeId;
	}

	public ChargeBatch getChargeBatch() {
		return this.chargeBatch;
	}

	public void setChargeBatch(ChargeBatch chargeBatch) {
		this.chargeBatch = chargeBatch;
	}


	public double getChargeAmount() {
		return this.chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<LineItem> getLineItems() {
		return this.lineItems;
	}

	public void setLineItems(Set<LineItem> lineItems) {
		this.lineItems = lineItems;
	}
}