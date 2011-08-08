package org.springsource.examples.expenses.reports;

import org.springsource.examples.expenses.fs.ManagedFile;

import javax.persistence.*;

/**
 * @author Josh Long
 */
@Entity
public class Attachment {
	private long attachmentId;
	private ManagedFile managedFile;
	private ExpenseReportLine expenseReportLine;
	private String description;

	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}