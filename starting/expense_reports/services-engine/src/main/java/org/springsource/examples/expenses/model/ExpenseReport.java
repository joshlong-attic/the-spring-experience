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
@Table(name = "expense_report", schema = "public")
public class ExpenseReport implements java.io.Serializable {
	private long expenseReportId;
	private ExpenseHolder expenseHolder;
	private String state;
	private Set<ExpenseReportLine> expenseReportLines = new HashSet<ExpenseReportLine>(0);
	private Set<ExpenseReportAuthorization> expenseReportAuthorizations = new HashSet<ExpenseReportAuthorization>(0);

	public ExpenseReport() {
	}

	public ExpenseReport(long expenseReportId, ExpenseHolder expenseHolder, String state) {
		this.expenseReportId = expenseReportId;
		this.expenseHolder = expenseHolder;
		this.state = state;
	}

	public ExpenseReport(long expenseReportId, ExpenseHolder expenseHolder, String state, Set<ExpenseReportLine> expenseReportLines, Set<ExpenseReportAuthorization> expenseReportAuthorizations) {
		this.expenseReportId = expenseReportId;
		this.expenseHolder = expenseHolder;
		this.state = state;
		this.expenseReportLines = expenseReportLines;
		this.expenseReportAuthorizations = expenseReportAuthorizations;
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
	@Column(name = "expense_report_id", unique = true, nullable = false)
	public long getExpenseReportId() {
		return this.expenseReportId;
	}

	public void setExpenseReportId(long expenseReportId) {
		this.expenseReportId = expenseReportId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_holder_id", nullable = false)
	public ExpenseHolder getExpenseHolder() {
		return this.expenseHolder;
	}

	public void setExpenseHolder(ExpenseHolder expenseHolder) {
		this.expenseHolder = expenseHolder;
	}

	@Column(name = "state", nullable = false, length = 20)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseReport")
	public Set<ExpenseReportLine> getExpenseReportLines() {
		return this.expenseReportLines;
	}

	public void setExpenseReportLines(Set<ExpenseReportLine> expenseReportLines) {
		this.expenseReportLines = expenseReportLines;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseReport")
	public Set<ExpenseReportAuthorization> getExpenseReportAuthorizations() {
		return this.expenseReportAuthorizations;
	}

	public void setExpenseReportAuthorizations(Set<ExpenseReportAuthorization> expenseReportAuthorizations) {
		this.expenseReportAuthorizations = expenseReportAuthorizations;
	}
}