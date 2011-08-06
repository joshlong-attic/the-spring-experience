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
	private Long version;
	private Charge charge;
	private ExpenseReport expenseReport;
	private Date dateCreated;
	private Date dateModified;
	private String justification;
	private boolean personalExpense;
	private boolean requiresReceipt;
	private Set<Attachment> attachments = new HashSet<Attachment>(0);

	public ExpenseReportLine() {
	}

	public ExpenseReportLine(long expenseReportLineId, Charge charge, ExpenseReport expenseReport, Date dateCreated, Date dateModified, String justification, boolean personalExpense, boolean requiresReceipt) {
		this.expenseReportLineId = expenseReportLineId;
		this.charge = charge;
		this.expenseReport = expenseReport;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.justification = justification;
		this.personalExpense = personalExpense;
		this.requiresReceipt = requiresReceipt;
	}

	public ExpenseReportLine(long expenseReportLineId, Charge charge, ExpenseReport expenseReport, Date dateCreated, Date dateModified, String justification, boolean personalExpense, boolean requiresReceipt, Set<Attachment> attachments) {
		this.expenseReportLineId = expenseReportLineId;
		this.charge = charge;
		this.expenseReport = expenseReport;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.justification = justification;
		this.personalExpense = personalExpense;
		this.requiresReceipt = requiresReceipt;
		this.attachments = attachments;
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

	@Version
	@Column(name = "version")
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
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

	@Column(name = "justification", nullable = false, length = 1000)
	public String getJustification() {
		return this.justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseReportLine")
	public Set<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
}