package org.springsource.examples.expenses.charges;


/**
 * a {@link Charge} is a free-standing entity that represents the boundry between an external credit card system and the
 * expense report system.  Essentially, charges may be selected and then added into {@link org.springsource.examples.expenses.reports.ExpenseReport}s.
 *
 * Ideally, once an expense report's been successfully {@link org.springsource.examples.expenses.reports.ExpenseReport#setClosed()}, then
 * the corresponding {@link Charge charge} would be {@link Charge#setReconciled() reconciled}.
 *
 * @author Josh Long
 */
public class Charge {
	private boolean reconciled;
	private double amount;
	private String category;
	private Long id;

	public boolean isReconciled() {
		return reconciled;
	}

	public double getAmount() {
		return amount;
	}

	public String getCategory() {
		return category;
	}

	public Long getId() {
		return id;
	}

	public Charge(Long id, double amount, String category) {
		this.id = id;
		this.amount = amount;
		this.category = category;
	}

	public void setReconciled(){
		this.reconciled = true ;
	}

}