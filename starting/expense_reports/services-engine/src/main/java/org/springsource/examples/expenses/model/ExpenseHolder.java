package org.springsource.examples.expenses.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Josh Long
 */
@Entity
@EntityListeners(org.springsource.examples.expenses.services.util.AuditingJpaEntityFieldListener.class)
@Table(name = "expense_holder", schema = "public")
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

	public ExpenseHolder() {
	}

	public ExpenseHolder(long expenseHolderId, String firstName, String lastName, String email, String password, double unjustifiedChargeAmountThreshold) {
		this.expenseHolderId = expenseHolderId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.unjustifiedChargeAmountThreshold = unjustifiedChargeAmountThreshold;
	}

	public ExpenseHolder(long expenseHolderId, ExpenseHolder authorizingExpenseHolder, String firstName, String lastName, String email, String password, double unjustifiedChargeAmountThreshold, Set<ExpenseReport> expenseReports, Set<CreditCard> creditCards, Set<ChargeBatch> chargeBatchs, Set<ExpenseReportAuthorization> expenseReportAuthorizations, Set<ExpenseHolder> expenseHolders) {
		this.expenseHolderId = expenseHolderId;
		this.authorizingExpenseHolder = authorizingExpenseHolder;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.unjustifiedChargeAmountThreshold = unjustifiedChargeAmountThreshold;
		this.expenseReports = expenseReports;
		this.creditCards = creditCards;
		this.chargeBatchs = chargeBatchs;
		this.expenseReportAuthorizations = expenseReportAuthorizations;
		this.expenseHolders = expenseHolders;
	}

	private java.util.Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 10)
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dc) {
		this.dateCreated = dc;
	}


	private java.util.Date dateModified;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", nullable = false, length = 10)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dc) {
		this.dateModified = dc;
	}


	private java.lang.Long version;

	@javax.persistence.Version
	public java.lang.Long getVersion() {
		return version;
	}

	public void setVersion(java.lang.Long value) {
		this.version = value;
	}

	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	@Column(name = "expense_holder_id", unique = true, nullable = false)
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

	@Column(name = "first_name", nullable = false)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", nullable = false, length = 1000)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "unjustified_charge_amount_threshold", nullable = false, precision = 17, scale = 17)
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