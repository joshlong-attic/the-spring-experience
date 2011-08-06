package org.springsource.examples.expenses.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Josh Long
 */
@Entity
@EntityListeners(org.springsource.examples.expenses.services.util.AuditingJpaEntityFieldListener.class)
@Table(name = "attachment", schema = "public")
public class Attachment implements java.io.Serializable {
	private long attachmentId;
	private Long version;
	private ManagedFile managedFile;
	private ExpenseReportLine expenseReportLine;
	private Date dateCreated;
	private Date dateModified;
	private String description;

	public Attachment() {
	}

	public Attachment(long attachmentId, ManagedFile managedFile, ExpenseReportLine expenseReportLine, Date dateCreated, Date dateModified, String description) {
		this.attachmentId = attachmentId;
		this.managedFile = managedFile;
		this.expenseReportLine = expenseReportLine;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.description = description;
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

	@Version
	@Column(name = "version")
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
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
}