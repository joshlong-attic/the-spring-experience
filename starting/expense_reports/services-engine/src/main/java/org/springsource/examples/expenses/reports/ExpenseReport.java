package org.springsource.examples.expenses.reports;

import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * aggregate for reconciled charges, which are called line items.
 *
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
    private String userId;

    /**
     * the state of the {@link ExpenseReport}
     */
    private ExpenseReportState state;

    /**
     * the line items for this expense report
     */
    private Set<LineItem> lineItems = new HashSet<LineItem>();

    /**
     * which states can the report be in?
     */
    public static enum ExpenseReportState {
        New, PendingReview, Closed
    }

    public ExpenseReport(String userId, ExpenseReportState state) {
        this.state = state;
        this.userId = userId;
        Assert.notNull(this.state, "the 'state' can't be null");
        Assert.notNull(this.userId, "the 'userId' can't be null");
    }

    public ExpenseReport(String userId) {
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
     */
     /* todo fixme after refactoring!

    protected boolean validate() {
        boolean valid = true;
        for (LineItem lineItem : getLineItems()) {
            boolean needsReceipt = lineItemValidationStrategy.lineItemRequiresReceipt(lineItem) && (lineItem.getAttachments().size() == 0);
            lineItem.setRequiresReceipt(needsReceipt);
            if (needsReceipt){
                valid = false;
            }
        }
        return valid;
    }
    */

    public boolean isValid() {
	    // todo fixme after refactoring
	    return false ;
     //    return validate();
    }

   public LineItem createLineItem (Charge charge ){
	  LineItem item  = new LineItem(charge.getChargeId(), charge.getAmount());
	   item.setCategory(charge.getVendor()) ;
	   getLineItems().add(item) ;
	   return item ;
   }


    public void fileReport (){
        Assert.isTrue(isValid(),"you can't submit an ExpenseReport unless it's valid");
        this.state = ExpenseReportState.PendingReview ;
    }

    public void rejectReport(){
        Assert.isTrue(!isValid(), "can't reject a valid report");
        this.state = ExpenseReportState.New ;
    }

    public void closeReport(){
        Assert.isTrue(isValid(),"you can't submit an ExpenseReport unless it's valid");
        this.state  = ExpenseReportState.Closed ;
    }
}