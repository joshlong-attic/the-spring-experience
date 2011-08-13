package org.springsource.examples.expenses.reports;

import org.springsource.examples.expenses.charges.Charge;
import org.springsource.examples.expenses.fs.ManagedFile;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link LineItem} is basically an unjustified, unreconciled {@link Charge}.
 *
 * @author Josh Long
 */

public class LineItem {
	private long expenseReportLineId;
	private Charge charge;
	private ExpenseReport expenseReport;
	private boolean personalExpense;
	private boolean requiresReceipt;
	private String justification;
	private Set<Attachment> attachments = new HashSet<Attachment>(0);


	public long getExpenseReportLineId() {
		return this.expenseReportLineId;
	}

	public void setExpenseReportLineId(long expenseReportLineId) {
		this.expenseReportLineId = expenseReportLineId;
	}

	public Charge getCharge() {
		return this.charge;
	}

	public void setCharge(Charge charge) {
		this.charge = charge;
	}

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

	public Set<Attachment> getAttachments() {
		return this.attachments;
	}


	public Attachment addAttachment(String description, ManagedFile file) {
		Attachment attachment = new Attachment();
		attachment.setManagedFile(file);
		attachment.setDescription(description);
		getAttachments().add(attachment);
		return attachment;
	}
}