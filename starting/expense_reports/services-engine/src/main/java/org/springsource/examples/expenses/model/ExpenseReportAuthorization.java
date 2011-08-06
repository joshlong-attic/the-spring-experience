package org.springsource.examples.expenses.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Josh Long
 */
@Entity
@EntityListeners(org.springsource.examples.expenses.services.util.AuditingJpaEntityFieldListener.class)
@Table(name = "expense_report_authorization", schema = "public")
public class ExpenseReportAuthorization implements java.io.Serializable {
	private long expenseReportAuthorizationId;
	private Long version;
	private ExpenseReport expenseReport;
	private Date approvalTime;
	private boolean approved;
	private long authorizingExpenseHolderId;
	private Date dateCreated;
	private Date dateModified;
	private String descriptionOfResponse;
	private boolean rejected;
	private Date rejectedTime;

	public ExpenseReportAuthorization() {
	}

	public ExpenseReportAuthorization(long expenseReportAuthorizationId, ExpenseReport expenseReport, Date approvalTime, boolean approved, long authorizingExpenseHolderId, Date dateCreated, Date dateModified, String descriptionOfResponse, boolean rejected, Date rejectedTime) {
		this.expenseReportAuthorizationId = expenseReportAuthorizationId;
		this.expenseReport = expenseReport;
		this.approvalTime = approvalTime;
		this.approved = approved;
		this.authorizingExpenseHolderId = authorizingExpenseHolderId;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.descriptionOfResponse = descriptionOfResponse;
		this.rejected = rejected;
		this.rejectedTime = rejectedTime;
	}

	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	@Column(name = "expense_report_authorization_id", unique = true, nullable = false)
	public long getExpenseReportAuthorizationId() {
		return this.expenseReportAuthorizationId;
	}

	public void setExpenseReportAuthorizationId(long expenseReportAuthorizationId) {
		this.expenseReportAuthorizationId = expenseReportAuthorizationId;
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
	@JoinColumn(name = "expense_report_id", nullable = false)
	public ExpenseReport getExpenseReport() {
		return this.expenseReport;
	}

	public void setExpenseReport(ExpenseReport expenseReport) {
		this.expenseReport = expenseReport;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_time", nullable = false, length = 29)
	public Date getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	@Column(name = "approved", nullable = false)
	public boolean isApproved() {
		return this.approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Column(name = "authorizing_expense_holder_id", nullable = false)
	public long getAuthorizingExpenseHolderId() {
		return this.authorizingExpenseHolderId;
	}

	public void setAuthorizingExpenseHolderId(long authorizingExpenseHolderId) {
		this.authorizingExpenseHolderId = authorizingExpenseHolderId;
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

	@Column(name = "description_of_response", nullable = false, length = 1000)
	public String getDescriptionOfResponse() {
		return this.descriptionOfResponse;
	}

	public void setDescriptionOfResponse(String descriptionOfResponse) {
		this.descriptionOfResponse = descriptionOfResponse;
	}

	@Column(name = "rejected", nullable = false)
	public boolean isRejected() {
		return this.rejected;
	}

	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rejected_time", nullable = false, length = 29)
	public Date getRejectedTime() {
		return this.rejectedTime;
	}

	public void setRejectedTime(Date rejectedTime) {
		this.rejectedTime = rejectedTime;
	}
}