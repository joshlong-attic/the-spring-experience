package org.springsource.examples.expenses.reports;

import org.springsource.examples.expenses.fs.ManagedFile;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link LineItem} is basically an unjustified, unreconciled {@link Charge}.
 *
 * @author Josh Long
 */

public class LineItem {
	private long lineItemId;
	private long chargeId;
	private ExpenseReport expenseReport;
	private boolean personalExpense;
	private boolean requiresReceipt;
    private double amount ;

    /**
     * does this conflcit with {@link #requiresReceipt}?
     */
    private  boolean valid ;

	private String category ;

	private Set<Attachment> attachments = new HashSet<Attachment>(0);

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getChargeId() {
        return chargeId;
    }

    public void setChargeId(long chargeId) {
        this.chargeId = chargeId;
    }

    public ExpenseReport getExpenseReport() {
        return expenseReport;
    }

    public void setExpenseReport(ExpenseReport expenseReport) {
        this.expenseReport = expenseReport;
    }

    public long getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(long lineItemId) {
        this.lineItemId = lineItemId;
    }

    public boolean isPersonalExpense() {
        return personalExpense;
    }

    public void setPersonalExpense(boolean personalExpense) {
        this.personalExpense = personalExpense;
    }

    public boolean isRequiresReceipt() {
        return requiresReceipt;
    }

    public void setRequiresReceipt(boolean requiresReceipt) {
        this.requiresReceipt = requiresReceipt;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Attachment addAttachment(String description, ManagedFile file) {
		Attachment attachment = new Attachment( file);
		attachment.setDescription(description);
		getAttachments().add(attachment);
        expenseReport.validate();
		return attachment;
	}
}