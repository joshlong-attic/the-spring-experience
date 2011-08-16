package org.springsource.examples.expenses.reports;


/**
 * a {@link Charge} is a free-standing entity that represents the boundry between an external credit card system and the
 * expense report system.  Essentially, charges may be selected and then added into {@link Expense}s.
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

	public Charge(Long id, boolean reconciled, double amount, String category) {
		this.reconciled = reconciled;
		this.id = id;
		this.amount = amount;
		this.category = category;
	}

}