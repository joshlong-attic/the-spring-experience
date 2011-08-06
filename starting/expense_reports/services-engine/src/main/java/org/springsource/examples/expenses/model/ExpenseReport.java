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
	private Long version;
	private ExpenseHolder expenseHolder;
	private Date dateCreated;
	private Date dateModified;
	private Set<ExpenseReportAuthorization> expenseReportAuthorizations = new HashSet<ExpenseReportAuthorization>(0);
	private Set<ExpenseReportLine> expenseReportLines = new HashSet<ExpenseReportLine>(0);

	public ExpenseReport() {
	}

	public ExpenseReport(long expenseReportId, ExpenseHolder expenseHolder, Date dateCreated, Date dateModified) {
		this.expenseReportId = expenseReportId;
		this.expenseHolder = expenseHolder;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public ExpenseReport(long expenseReportId, ExpenseHolder expenseHolder, Date dateCreated, Date dateModified, Set<ExpenseReportAuthorization> expenseReportAuthorizations, Set<ExpenseReportLine> expenseReportLines) {
		this.expenseReportId = expenseReportId;
		this.expenseHolder = expenseHolder;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.expenseReportAuthorizations = expenseReportAuthorizations;
		this.expenseReportLines = expenseReportLines;
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

	@Version
	@Column(name = "version")
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_holder_id", nullable = false)
	public ExpenseHolder getExpenseHolder() {
		return this.expenseHolder;
	}

	public void setExpenseHolder(ExpenseHolder expenseHolder) {
		this.expenseHolder = expenseHolder;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseReport")
	public Set<ExpenseReportAuthorization> getExpenseReportAuthorizations() {
		return this.expenseReportAuthorizations;
	}

	public void setExpenseReportAuthorizations(Set<ExpenseReportAuthorization> expenseReportAuthorizations) {
		this.expenseReportAuthorizations = expenseReportAuthorizations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseReport")
	public Set<ExpenseReportLine> getExpenseReportLines() {
		return this.expenseReportLines;
	}

	public void setExpenseReportLines(Set<ExpenseReportLine> expenseReportLines) {
		this.expenseReportLines = expenseReportLines;
	}
}