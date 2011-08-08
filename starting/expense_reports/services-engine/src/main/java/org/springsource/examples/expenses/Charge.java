package org.springsource.examples.expenses;

import org.springsource.examples.expenses.expenses.ExpenseReportLine;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Josh Long
 */
@Entity
@Table(name = "charge", schema = "public")
public class Charge implements java.io.Serializable {
	private long chargeId;
	private ChargeBatch chargeBatch;
	private double chargeAmount;
	private String description;
	private Set<ExpenseReportLine> expenseReportLines = new HashSet<ExpenseReportLine>(0);

	public Charge() {
	}

	public Charge(long chargeId, ChargeBatch chargeBatch, double chargeAmount, String description) {
		this.chargeId = chargeId;
		this.chargeBatch = chargeBatch;
		this.chargeAmount = chargeAmount;
		this.description = description;
	}

	public Charge(long chargeId, ChargeBatch chargeBatch, double chargeAmount, String description, Set<ExpenseReportLine> expenseReportLines) {
		this.chargeId = chargeId;
		this.chargeBatch = chargeBatch;
		this.chargeAmount = chargeAmount;
		this.description = description;
		this.expenseReportLines = expenseReportLines;
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
	@Column(name = "charge_id", unique = true, nullable = false)
	public long getChargeId() {
		return this.chargeId;
	}

	public void setChargeId(long chargeId) {
		this.chargeId = chargeId;
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