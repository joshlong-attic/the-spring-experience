package org.springsource.examples.expenses.reports;

import org.springsource.examples.expenses.users.ExpenseHolder;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Josh Long
 */
@Entity
public class ExpenseReportAuthorization   {
	private long expenseReportAuthorizationId;
	private ExpenseHolder authorizingExpenseHolder;
	private ExpenseReport expenseReport;
	private Date approvedTime;
	private boolean approved;
	private boolean rejected;
	private Date rejectedTime;
	private String descriptionOfResponse;
	private boolean requiresResponse;


	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
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
	public Date getApprovedTime() {
		return this.approvedTime;
	}

	public void setApprovedTime(Date approvedTime) {
		this.approvedTime = approvedTime;
	}

	public boolean isApproved() {
		return this.approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

 	public boolean isRejected() {
		return this.rejected;
	}

	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}

	@Temporal(TemporalType.TIMESTAMP)
 	public Date getRejectedTime() {
		return this.rejectedTime;
	}

	public void setRejectedTime(Date rejectedTime) {
		this.rejectedTime = rejectedTime;
	}

 	public String getDescriptionOfResponse() {
		return this.descriptionOfResponse;
	}

	public void setDescriptionOfResponse(String descriptionOfResponse) {
		this.descriptionOfResponse = descriptionOfResponse;
	}

 	public boolean isRequiresResponse() {
		return this.requiresResponse;
	}

	public void setRequiresResponse(boolean requiresResponse) {
		this.requiresResponse = requiresResponse;
	}
}