package org.springsource.examples.expenses.users;

import org.springsource.examples.expenses.charges.ChargeBatch;
import org.springsource.examples.expenses.reports.ExpenseReport;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Describes the actor that creates and is liable for expenses. A {@link User user }
 * must reconcile those {@link org.springsource.examples.expenses.charges.Charge charges}
 * in the {@link ExpenseReport expense reports} that he or she submits.
 *
 * @author Josh Long
 */
public class User {
	private long userId;
	private User authorizingUser;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private double expensableAmountWithoutReceipt;
	private Set<ExpenseReport> expenseReports = new HashSet<ExpenseReport>(0);
	private Set<CreditCard> creditCards = new HashSet<CreditCard>(0);
	private Set<ChargeBatch> chargeBatchs = new HashSet<ChargeBatch>(0);
	private Set<User> users = new HashSet<User>(0);

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public User getAuthorizingUser() {
		return this.authorizingUser;
	}

	public void setAuthorizingUser(User authorizingUser) {
		this.authorizingUser = authorizingUser;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getExpensableAmountWithoutReceipt() {
		return this.expensableAmountWithoutReceipt;
	}

	public void setExpensableAmountWithoutReceipt(double expensableAmountWithoutReceipt) {
		this.expensableAmountWithoutReceipt = expensableAmountWithoutReceipt;
	}


	public Set<ExpenseReport> getExpenseReports() {
		return this.expenseReports;
	}

	public void setExpenseReports(Set<ExpenseReport> expenseReports) {
		this.expenseReports = expenseReports;
	}

	public Set<CreditCard> getCreditCards() {
		return this.creditCards;
	}

	public void setCreditCards(Set<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	public Set<ChargeBatch> getChargeBatchs() {
		return this.chargeBatchs;
	}

	public void setChargeBatchs(Set<ChargeBatch> chargeBatchs) {
		this.chargeBatchs = chargeBatchs;
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}