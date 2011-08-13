package org.springsource.examples.expenses.charges;

import org.springsource.examples.expenses.users.CreditCard;
import org.springsource.examples.expenses.users.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * An aggregate object for {@link Charge} objects.
 * <p/>
 * Might not be surfaced to the user directly, but serves as a useful
 * aggregation of imported charges. ("Once per day, per user"? "Last month"? etc.)
 *
 * @author Josh Long
 */
public class ChargeBatch {
	private long chargeBatchId;
	private User user;
	private Date importTime;
	private Set<Charge> charges = new HashSet<Charge>(0);

	public Charge addCharge(CreditCard cc, double chargeAmt, String source) {
		Charge c = new Charge();
		c.setChargeAmount(chargeAmt);
		c.setDescription(source);
		c.setCreditCard(cc);
		c.setChargeBatch(this);
		getCharges().add(c);
		return c;
	}

	public long getChargeBatchId() {
		return this.chargeBatchId;
	}

	public void setChargeBatchId(long chargeBatchId) {
		this.chargeBatchId = chargeBatchId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getImportTime() {
		return this.importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public Set<Charge> getCharges() {
		return this.charges;
	}

	public void setCharges(Set<Charge> charges) {
		this.charges = charges;
	}
}