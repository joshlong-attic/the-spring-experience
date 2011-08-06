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
	private Long version;
	private ExpenseHolder authorizingExpenseHolder;
	private Date dateCreated;
	private Date dateModified;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private double unjustifiedChargeAmountThreshold;
	private Set<ExpenseReport> expenseReports = new HashSet<ExpenseReport>(0);
	private Set<ChargeBatch> chargeBatchs = new HashSet<ChargeBatch>(0);
	private Set<CreditCard> creditCards = new HashSet<CreditCard>(0);
	private Set<ExpenseHolder> expenseHolders = new HashSet<ExpenseHolder>(0);

	public ExpenseHolder() {
	}

	public ExpenseHolder(long expenseHolderId, Date dateCreated, Date dateModified, String email, String firstName, String lastName, String password, double unjustifiedChargeAmountThreshold) {
		this.expenseHolderId = expenseHolderId;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.unjustifiedChargeAmountThreshold = unjustifiedChargeAmountThreshold;
	}

	public ExpenseHolder(long expenseHolderId, ExpenseHolder authorizingExpenseHolder, Date dateCreated, Date dateModified, String email, String firstName, String lastName, String password, double unjustifiedChargeAmountThreshold, Set<ExpenseReport> expenseReports, Set<ChargeBatch> chargeBatchs, Set<CreditCard> creditCards, Set<ExpenseHolder> expenseHolders) {
		this.expenseHolderId = expenseHolderId;
		this.authorizingExpenseHolder = authorizingExpenseHolder;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.unjustifiedChargeAmountThreshold = unjustifiedChargeAmountThreshold;
		this.expenseReports = expenseReports;
		this.chargeBatchs = chargeBatchs;
		this.creditCards = creditCards;
		this.expenseHolders = expenseHolders;
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

	@Version
	@Column(name = "version")
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authorizing_expense_holder_id")
	public ExpenseHolder getAuthorizingExpenseHolder() {
		return this.authorizingExpenseHolder;
	}

	public void setAuthorizingExpenseHolder(ExpenseHolder authorizingExpenseHolder) {
		this.authorizingExpenseHolder = authorizingExpenseHolder;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 29)
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", nullable = false, length = 29)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	public Set<ChargeBatch> getChargeBatchs() {
		return this.chargeBatchs;
	}

	public void setChargeBatchs(Set<ChargeBatch> chargeBatchs) {
		this.chargeBatchs = chargeBatchs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseHolder")
	public Set<CreditCard> getCreditCards() {
		return this.creditCards;
	}

	public void setCreditCards(Set<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "authorizingExpenseHolder")
	public Set<ExpenseHolder> getExpenseHolders() {
		return this.expenseHolders;
	}

	public void setExpenseHolders(Set<ExpenseHolder> expenseHolders) {
		this.expenseHolders = expenseHolders;
	}
}