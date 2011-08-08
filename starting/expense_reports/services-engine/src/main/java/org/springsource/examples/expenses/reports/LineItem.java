package org.springsource.examples.expenses.reports;

import org.springsource.examples.expenses.charges.Charge;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * A {@link LineItem} is basically an unjustified, unreconciled {@link Charge}.
 *
 * @author Josh Long
 */
@Entity
public class LineItem {
	private long expenseReportLineId;
	private Charge charge;
	private ExpenseReport expenseReport;
	private boolean personalExpense;
	private boolean requiresReceipt;
	private String justification;
	private Set<Attachment> attachments = new HashSet<Attachment>(0);


	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	public long getExpenseReportLineId() {
		return this.expenseReportLineId;
	}

	public void setExpenseReportLineId(long expenseReportLineId) {
		this.expenseReportLineId = expenseReportLineId;
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

	public boolean isPersonalExpense() {
		return this.personalExpense;
	}

	public void setPersonalExpense(boolean personalExpense) {
		this.personalExpense = personalExpense;
	}

	public boolean isRequiresReceipt() {
		return this.requiresReceipt;
	}

	public void setRequiresReceipt(boolean requiresReceipt) {
		this.requiresReceipt = requiresReceipt;
	}

	public String getJustification() {
		return this.justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lineItem")
	public Set<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
}