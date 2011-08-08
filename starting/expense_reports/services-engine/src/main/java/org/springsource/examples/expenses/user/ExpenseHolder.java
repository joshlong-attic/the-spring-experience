package org.springsource.examples.expenses.user;

import org.springsource.examples.expenses.ChargeBatch;
import org.springsource.examples.expenses.expenses.ExpenseReport;
import org.springsource.examples.expenses.expenses.ExpenseReportAuthorization;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Josh Long
 */
@Entity
public class ExpenseHolder implements java.io.Serializable {
	private long expenseHolderId;
	private ExpenseHolder authorizingExpenseHolder;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private double unjustifiedChargeAmountThreshold;
	private Set<ExpenseReport> expenseReports = new HashSet<ExpenseReport>(0);
	private Set<CreditCard> creditCards = new HashSet<CreditCard>(0);
	private Set<ChargeBatch> chargeBatchs = new HashSet<ChargeBatch>(0);
	private Set<ExpenseReportAuthorization> expenseReportAuthorizations = new HashSet<ExpenseReportAuthorization>(0);
	private Set<ExpenseHolder> expenseHolders = new HashSet<ExpenseHolder>(0);



	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	public long getExpenseHolderId() {
		return this.expenseHolderId;
	}

	public void setExpenseHolderId(long expenseHolderId) {
		this.expenseHolderId = expenseHolderId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authorizing_expense_holder_id")
	public ExpenseHolder getAuthorizingExpenseHolder() {
		return this.authorizingExpenseHolder;
	}

	public void setAuthorizingExpenseHolder(ExpenseHolder authorizingExpenseHolder) {
		this.authorizingExpenseHolder = authorizingExpenseHolder;
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

	public double getUnjustifiedChargeAmountThreshold() {
		return this.unjustifiedChargeAmountThreshold;
	}

	public void setUnjustifiedChargeAmountThreshold(double unjustifiedChargeAmountThreshold) {
		this.unjustifiedChargeAmountThreshold = unjustifiedChargeAmountThreshold;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseHolder")
	public Set<ExpenseReport> getExpenseReports() {
		return this.expenseReports;
	}

	public void setExpenseReports(Set<ExpenseReport> expenseReports) {
		this.expenseReports = expenseReports;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseHolder")
	public Set<CreditCard> getCreditCards() {
		return this.creditCards;
	}

	public void setCreditCards(Set<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseHolder")
	public Set<ChargeBatch> getChargeBatchs() {
		return this.chargeBatchs;
	}

	public void setChargeBatchs(Set<ChargeBatch> chargeBatchs) {
		this.chargeBatchs = chargeBatchs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "authorizingExpenseHolder")
	public Set<ExpenseReportAuthorization> getExpenseReportAuthorizations() {
		return this.expenseReportAuthorizations;
	}

	public void setExpenseReportAuthorizations(Set<ExpenseReportAuthorization> expenseReportAuthorizations) {
		this.expenseReportAuthorizations = expenseReportAuthorizations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "authorizingExpenseHolder")
	public Set<ExpenseHolder> getExpenseHolders() {
		return this.expenseHolders;
	}

	public void setExpenseHolders(Set<ExpenseHolder> expenseHolders) {
		this.expenseHolders = expenseHolders;
	}
}