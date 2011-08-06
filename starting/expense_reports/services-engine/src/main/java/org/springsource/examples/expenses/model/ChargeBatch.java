package org.springsource.examples.expenses.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Josh Long
 */
@Entity
@EntityListeners(org.springsource.examples.expenses.services.util.AuditingJpaEntityFieldListener.class)
@Table(name = "charge_batch", schema = "public")
public class ChargeBatch implements java.io.Serializable {
	private long chargeBatchId;
	private Long version;
	private ExpenseHolder expenseHolder;
	private Date dateCreated;
	private Date dateModified;
	private Date importTime;
	private Set<Charge> charges = new HashSet<Charge>(0);

	public ChargeBatch() {
	}

	public ChargeBatch(long chargeBatchId, ExpenseHolder expenseHolder, Date dateCreated, Date dateModified, Date importTime) {
		this.chargeBatchId = chargeBatchId;
		this.expenseHolder = expenseHolder;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.importTime = importTime;
	}

	public ChargeBatch(long chargeBatchId, ExpenseHolder expenseHolder, Date dateCreated, Date dateModified, Date importTime, Set<Charge> charges) {
		this.chargeBatchId = chargeBatchId;
		this.expenseHolder = expenseHolder;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.importTime = importTime;
		this.charges = charges;
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

	@Version
	@Column(name = "version")
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
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
	@Column(name = "date_created", nullable = false, length = 29)
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", nullable = false, length = 29)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
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