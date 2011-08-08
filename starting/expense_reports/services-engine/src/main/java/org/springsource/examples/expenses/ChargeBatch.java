package org.springsource.examples.expenses;

import org.springsource.examples.expenses.user.ExpenseHolder;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Josh Long
 */
@Entity
@Table(name = "charge_batch", schema = "public")
public class ChargeBatch implements java.io.Serializable {
	private long chargeBatchId;
	private ExpenseHolder expenseHolder;
	private Date importTime;
	private Set<Charge> charges = new HashSet<Charge>(0);

	public ChargeBatch() {
	}

	public ChargeBatch(long chargeBatchId, ExpenseHolder expenseHolder, Date importTime) {
		this.chargeBatchId = chargeBatchId;
		this.expenseHolder = expenseHolder;
		this.importTime = importTime;
	}

	public ChargeBatch(long chargeBatchId, ExpenseHolder expenseHolder, Date importTime, Set<Charge> charges) {
		this.chargeBatchId = chargeBatchId;
		this.expenseHolder = expenseHolder;
		this.importTime = importTime;
		this.charges = charges;
	}

	private java.util.Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 10)
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dc) {
		this.dateCreated = dc;
	}


	private java.util.Date dateModified;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", nullable = false, length = 10)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dc) {
		this.dateModified = dc;
	}


	private java.lang.Long version;

	@javax.persistence.Version
	public java.lang.Long getVersion() {
		return version;
	}

	public void setVersion(java.lang.Long value) {
		this.version = value;
	}

	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	@Column(name = "charge_batch_id", unique = true, nullable = false)
	public long getChargeBatchId() {
		return this.chargeBatchId;
	}

	public void setChargeBatchId(long chargeBatchId) {
		this.chargeBatchId = chargeBatchId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_holder_id", nullable = false)
	public ExpenseHolder getExpenseHolder() {
		return this.expenseHolder;
	}

	public void setExpenseHolder(ExpenseHolder expenseHolder) {
		this.expenseHolder = expenseHolder;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "import_time", nullable = false, length = 29)
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