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
	private ExpenseHolder authorizingExpenseHolder;
	private ExpenseReport expenseReport;
	private Date approvedTime;
	private boolean approved;
	private boolean rejected;
	private Date rejectedTime;
	private String descriptionOfResponse;
	private boolean requiresResponse;

	public ExpenseReportAuthorization() {
	}

	public ExpenseReportAuthorization(long expenseReportAuthorizationId, ExpenseHolder authorizingExpenseHolder, ExpenseReport expenseReport, boolean approved, boolean rejected, boolean requiresResponse) {
		this.expenseReportAuthorizationId = expenseReportAuthorizationId;
		this.authorizingExpenseHolder = authorizingExpenseHolder;
		this.expenseReport = expenseReport;
		this.approved = approved;
		this.rejected = rejected;
		this.requiresResponse = requiresResponse;
	}

	public ExpenseReportAuthorization(long expenseReportAuthorizationId, ExpenseHolder authorizingExpenseHolder, ExpenseReport expenseReport, Date approvedTime, boolean approved, boolean rejected, Date rejectedTime, String descriptionOfResponse, boolean requiresResponse) {
		this.expenseReportAuthorizationId = expenseReportAuthorizationId;
		this.authorizingExpenseHolder = authorizingExpenseHolder;
		this.expenseReport = expenseReport;
		this.approvedTime = approvedTime;
		this.approved = approved;
		this.rejected = rejected;
		this.rejectedTime = rejectedTime;
		this.descriptionOfResponse = descriptionOfResponse;
		this.requiresResponse = requiresResponse;
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
	@Column(name = "expense_report_authorization_id", unique = true, nullable = false)
	public long getExpenseReportAuthorizationId() {
		return this.expenseReportAuthorizationId;
	}

	public void setExpenseReportAuthorizationId(long expenseReportAuthorizationId) {
		this.expenseReportAuthorizationId = expenseReportAuthorizationId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authorizing_expense_holder_id", nullable = false)
	public ExpenseHolder getAuthorizingExpenseHolder() {
		return this.authorizingExpenseHolder;
	}

	public void setAuthorizingExpenseHolder(ExpenseHolder authorizingExpenseHolder) {
		this.authorizingExpenseHolder = authorizingExpenseHolder;
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
	@Column(name = "approved_time", length = 29)
	public Date getApprovedTime() {
		return this.approvedTime;
	}

	public void setApprovedTime(Date approvedTime) {
		this.approvedTime = approvedTime;
	}

	@Column(name = "approved", nullable = false)
	public boolean isApproved() {
		return this.approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Column(name = "rejected", nullable = false)
	public boolean isRejected() {
		return this.rejected;
	}

	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rejected_time", length = 29)
	public Date getRejectedTime() {
		return this.rejectedTime;
	}

	public void setRejectedTime(Date rejectedTime) {
		this.rejectedTime = rejectedTime;
	}

	@Column(name = "description_of_response", length = 1000)
	public String getDescriptionOfResponse() {
		return this.descriptionOfResponse;
	}

	public void setDescriptionOfResponse(String descriptionOfResponse) {
		this.descriptionOfResponse = descriptionOfResponse;
	}

	@Column(name = "requires_response", nullable = false)
	public boolean isRequiresResponse() {
		return this.requiresResponse;
	}

	public void setRequiresResponse(boolean requiresResponse) {
		this.requiresResponse = requiresResponse;
	}
}