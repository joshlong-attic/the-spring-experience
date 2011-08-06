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
@Table(name = "expense_report_line", schema = "public")
public class ExpenseReportLine implements java.io.Serializable {
	private long expenseReportLineId;
	private Charge charge;
	private ExpenseReport expenseReport;
	private boolean personalExpense;
	private boolean requiresReceipt;
	private String justification;
	private Set<Attachment> attachments = new HashSet<Attachment>(0);

	public ExpenseReportLine() {
	}

	public ExpenseReportLine(long expenseReportLineId, Charge charge, ExpenseReport expenseReport, boolean personalExpense, boolean requiresReceipt) {
		this.expenseReportLineId = expenseReportLineId;
		this.charge = charge;
		this.expenseReport = expenseReport;
		this.personalExpense = personalExpense;
		this.requiresReceipt = requiresReceipt;
	}

	public ExpenseReportLine(long expenseReportLineId, Charge charge, ExpenseReport expenseReport, boolean personalExpense, boolean requiresReceipt, String justification, Set<Attachment> attachments) {
		this.expenseReportLineId = expenseReportLineId;
		this.charge = charge;
		this.expenseReport = expenseReport;
		this.personalExpense = personalExpense;
		this.requiresReceipt = requiresReceipt;
		this.justification = justification;
		this.attachments = attachments;
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
	@Column(name = "expense_report_line_id", unique = true, nullable = false)
	public long getExpenseReportLineId() {
		return this.expenseReportLineId;
	}

	public void setExpenseReportLineId(long expenseReportLineId) {
		this.expenseReportLineId = expenseReportLineId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_id", nullable = false)
	public Charge getCharge() {
		return this.charge;
	}

	public void setCharge(Charge charge) {
		this.charge = charge;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_report_id", nullable = false)
	public ExpenseReport getExpenseReport() {
		return this.expenseReport;
	}

	public void setExpenseReport(ExpenseReport expenseReport) {
		this.expenseReport = expenseReport;
	}

	@Column(name = "personal_expense", nullable = false)
	public boolean isPersonalExpense() {
		return this.personalExpense;
	}

	public void setPersonalExpense(boolean personalExpense) {
		this.personalExpense = personalExpense;
	}

	@Column(name = "requires_receipt", nullable = false)
	public boolean isRequiresReceipt() {
		return this.requiresReceipt;
	}

	public void setRequiresReceipt(boolean requiresReceipt) {
		this.requiresReceipt = requiresReceipt;
	}

	@Column(name = "justification", length = 1000)
	public String getJustification() {
		return this.justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseReportLine")
	public Set<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
}