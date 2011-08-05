package org.springsource.examples.expenses.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Attachment generated by hbm2java
 */
@Entity
@EntityListeners(org.springsource.examples.expenses.services.util.AuditingJpaEntityFieldListener.class)
@Table(name = "attachment", schema = "public")
public class Attachment implements java.io.Serializable {
	private long attachmentId;
	private ManagedFile managedFile;
	private ExpenseReportLine expenseReportLine;

	public Attachment() {
	}

	public Attachment(long attachmentId, ManagedFile managedFile, ExpenseReportLine expenseReportLine) {
		this.attachmentId = attachmentId;
		this.managedFile = managedFile;
		this.expenseReportLine = expenseReportLine;
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
	@Column(name = "attachment_id", unique = true, nullable = false)
	public long getAttachmentId() {
		return this.attachmentId;
	}

	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "managed_file_id", nullable = false)
	public ManagedFile getManagedFile() {
		return this.managedFile;
	}

	public void setManagedFile(ManagedFile managedFile) {
		this.managedFile = managedFile;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_report_line_id", nullable = false)
	public ExpenseReportLine getExpenseReportLine() {
		return this.expenseReportLine;
	}

	public void setExpenseReportLine(ExpenseReportLine expenseReportLine) {
		this.expenseReportLine = expenseReportLine;
	}
}