package org.springsource.examples.expenses.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Josh Long
 */
@Entity
@EntityListeners(org.springsource.examples.expenses.services.util.AuditingJpaEntityFieldListener.class)
@Table(name = "credit_card", schema = "public")
public class CreditCard implements java.io.Serializable {
	private long creditCardId;
	private Long version;
	private ExpenseHolder expenseHolder;
	private Date dateCreated;
	private Date dateModified;

	public CreditCard() {
	}

	public CreditCard(long creditCardId, ExpenseHolder expenseHolder, Date dateCreated, Date dateModified) {
		this.creditCardId = creditCardId;
		this.expenseHolder = expenseHolder;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	@Column(name = "credit_card_id", unique = true, nullable = false)
	public long getCreditCardId() {
		return this.creditCardId;
	}

	public void setCreditCardId(long creditCardId) {
		this.creditCardId = creditCardId;
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
	@JoinColumn(name = "expense_holder_id", nullable = false)
	public ExpenseHolder getExpenseHolder() {
		return this.expenseHolder;
	}

	public void setExpenseHolder(ExpenseHolder expenseHolder) {
		this.expenseHolder = expenseHolder;
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
}