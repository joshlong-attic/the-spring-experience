package org.springsource.examples.expenses.reports;

import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Josh Long
 */

public class ExpenseReport {

    /**
     * the validation strategy
     */
    private LineItemValidationStrategy lineItemValidationStrategy = new DefaultLineItemValidationStrategy();

    /**
     * the id of this expense report
     */
    private long expenseReportId;

    /**
     * the user ID
     */
    private String userId ;

    /**
     * the state of the {@link ExpenseReport}
     */
    private ExpenseReportState state;

    /**
     *  the line items for this expense report
     */
    private Set<LineItem> lineItems = new HashSet<LineItem>();

    /**
     * which states can the report be in?
     */
    public static enum ExpenseReportState {
        New, PendingReview, Rejected , Closed
    }

    public ExpenseReport(String  userId, ExpenseReportState state) {
        this.state = state;
        this.userId = userId;
        Assert.notNull(this.state, "the 'state' can't be null");
        Assert.notNull(this.userId,  "the 'userId' can't be null");
    }

    public ExpenseReport(String  userId ) {
        this(userId, ExpenseReportState.New);
    }

    public long getExpenseReportId() {
        return expenseReportId;
    }

    public void setExpenseReportId(long expenseReportId) {
        this.expenseReportId = expenseReportId;
    }

    public Set<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItemValidationStrategy(LineItemValidationStrategy lineItemValidationStrategy) {
        this.lineItemValidationStrategy = lineItemValidationStrategy;
    }

    public ExpenseReportState getState() {
        return state;
    }

    /**
     * reassess the state of this entity
     *
     * @return is the {@code ExpenseReport} in a known state.
     *
     */
    protected boolean validate(){
       boolean valid = true;
        for (LineItem li : getLineItems()) {
            boolean liValid = lineItemValidationStrategy.lineItemRequiresReceipt(li) && (li.getAttachments().size() == 0);
            li.setRequiresReceipt(liValid);
            if (!liValid)
                valid = false;
        }
        return valid;
    }

    public boolean isValid() {
      return validate();
    }

    public LineItem addLineItem(LineItem li) {
        getLineItems().add(li);
        validate();
        return li;
    }

    /**
     * We have a {@link Charge charge} entity, but this model should not be afflcited with any knowledge of that entity.
     *
     * It is merely the interface or boundry entity between systems. This system doesn't have a concept of a charge entity
     *
     * @param chargeId the ID fo the charge (which can then be used to later consult the details of the charge)
     * @param chargeAmt the amount of the charge (copied wholesale from the charge)
     * @return a {@link LineItem}
     */
    public LineItem addLineItemFromCharge(long chargeId, double chargeAmt) {
        LineItem li = new LineItem();
        li.setChargeId(chargeId);
        li.setExpenseReport(this);
        li.setAmount(chargeAmt);
        return addLineItem(li);
    }
}