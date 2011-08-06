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
@Table(name = "charge", schema = "public")
public class Charge implements java.io.Serializable {
	private long chargeId;
	private Long version;
	private ChargeBatch chargeBatch;
	private double chargeAmount;
	private Date dateCreated;
	private Date dateModified;
	private String description;
	private Set<ExpenseReportLine> expenseReportLines = new HashSet<ExpenseReportLine>(0);

	public Charge() {
	}

	public Charge(long chargeId, ChargeBatch chargeBatch, double chargeAmount, Date dateCreated, Date dateModified, String description) {
		this.chargeId = chargeId;
		this.chargeBatch = chargeBatch;
		this.chargeAmount = chargeAmount;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.description = description;
	}

	public Charge(long chargeId, ChargeBatch chargeBatch, double chargeAmount, Date dateCreated, Date dateModified, String description, Set<ExpenseReportLine> expenseReportLines) {
		this.chargeId = chargeId;
		this.chargeBatch = chargeBatch;
		this.chargeAmount = chargeAmount;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.description = description;
		this.expenseReportLines = expenseReportLines;
	}

	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	@Column(name = "charge_id", unique = true, nullable = false)
	public long getChargeId() {
		return this.chargeId;
	}

	public void setChargeId(long chargeId) {
		this.chargeId = chargeId;
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
	@JoinColumn(name = "charge_batch_id", nullable = false)
	public ChargeBatch getChargeBatch() {
		return this.chargeBatch;
	}

	public void setChargeBatch(ChargeBatch chargeBatch) {
		this.chargeBatch = chargeBatch;
	}

	@Column(name = "charge_amount", nullable = false, precision = 17, scale = 17)
	public double getChargeAmount() {
		return this.chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
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

	@Column(name = "description", nullable = false, length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "charge")
	public Set<ExpenseReportLine> getExpenseReportLines() {
		return this.expenseReportLines;
	}

	public void setExpenseReportLines(Set<ExpenseReportLine> expenseReportLines) {
		this.expenseReportLines = expenseReportLines;
	}
}