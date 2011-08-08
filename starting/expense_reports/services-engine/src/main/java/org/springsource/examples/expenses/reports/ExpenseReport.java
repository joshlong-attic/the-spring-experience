package org.springsource.examples.expenses.reports;

import org.springsource.examples.expenses.users.ExpenseHolder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Josh Long
 */
@Entity
public class ExpenseReport   {
	private long expenseReportId;
	private ExpenseHolder expenseHolder;
	private String state;
	private Set<ExpenseReportLine> expenseReportLines = new HashSet<ExpenseReportLine>(0);
	private Set<ExpenseReportAuthorization> expenseReportAuthorizations = new HashSet<ExpenseReportAuthorization>(0);

	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
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