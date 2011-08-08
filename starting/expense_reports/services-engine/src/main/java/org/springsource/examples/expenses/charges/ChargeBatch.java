package org.springsource.examples.expenses.charges;

import org.springsource.examples.expenses.users.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * An aggregate object for {@link Charge} objects.
 *
 * Might not be surfaced to the user directly, but serves as a useful
 * aggregation of imported charges. ("Once per day, per user"? "Last month"? etc.)
 *
 * @author Josh Long
 */
@Entity
public class ChargeBatch {
	private long chargeBatchId;
	private User user;
	private Date importTime;
	private Set<Charge> charges = new HashSet<Charge>(0);


	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	public long getChargeBatchId() {
		return this.chargeBatchId;
	}

	public void setChargeBatchId(long chargeBatchId) {
		this.chargeBatchId = chargeBatchId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getImportTime() {
		return this.importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chargeBatch")
	public Set<Charge> getCharges() {
		return this.charges;
	}

	public void setCharges(Set<Charge> charges) {
		this.charges = charges;
	}
}