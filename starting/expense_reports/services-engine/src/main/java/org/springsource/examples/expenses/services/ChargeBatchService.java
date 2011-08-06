package org.springsource.examples.expenses.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.expenses.model.Charge;
import org.springsource.examples.expenses.model.ChargeBatch;
import org.springsource.examples.expenses.model.ExpenseHolder;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * service to manage {@link org.springsource.examples.expenses.model.Charge charges} and {@link org.springsource.examples.expenses.model.ChargeBatch charge batches}.
 *
 * @author Josh Long
 */
@Service
public class ChargeBatchService {

	@PersistenceContext private EntityManager entityManager;

	@Inject private ExpenseHolderService expenseHolderService;

	/**
	 * creates a charge for a charge batch. A single charge typically corresponds to a charge on a credit card.
	 *
	 * Perhaps there are other sources that can be expensed? Perhaps companies give their employees company checkbooks?
	 *
	 * This might then correspond to a charge written against a company check. Either way, it
	 * represents a unique, isolated, quantifiable charge (perhaps with a description from the vendor, e.g., "Starbucks ventura blvd").
	 *
	 * @param chargeBatchId the id of the charge batch
	 * @param chargeAmt the liability amount that this charge represents to the {@link ExpenseHolder}
	 * @param description a description of the charge (as perhaps might appear on the credit card company's line items). Example: "starbucks ventura blvd 5555"
	 * @return a {@link Charge} object (that has been attached the appropriate {@link ChargeBatch}
	 */
	@Transactional
	public Charge createCharge( long chargeBatchId, double chargeAmt, String description){
		ChargeBatch batch = getChargeBatchById(chargeBatchId) ;
		Charge c = new Charge() ;
		c.setChargeAmount(chargeAmt);
		c.setChargeBatch( batch );
		c.setDescription(description);

		entityManager.persist(c);

		return c;
	}

	@Transactional(readOnly = true)
	public ChargeBatch getChargeBatchById(long chargeBatchId) {
		return entityManager.find(ChargeBatch.class, chargeBatchId);
	}

	@Transactional
	public void setChargeBatchImportTime(long chargeBatchId, Date importTimeStamp) {

		ChargeBatch chargeBatch = entityManager.find(ChargeBatch.class, chargeBatchId);
		chargeBatch.setImportTime(importTimeStamp);

		entityManager.merge(chargeBatch);
	}

	/**
	 * Umbrella object. Represents a grouping of a specific time, as well as a specific {@link ExpenseHolder}.
	 *
	 * You might imagine that a system imports {@link Charge}s as part of a {@link ChargeBatch} every day at midnight.
	 *
	 * This simply provides a convenient way to group chronic imports.
	 *
	 * The charge batch may or may not be an abstraction that the user of the system needs to know about.
	 *
	 * @param expenseHolderId the id of the expense holder
	 * @param importTimeStamp when this batch was created.
	 * @return a {@link ChargeBatch} collecting all the charges in it.
	 */
	@Transactional
	public ChargeBatch createChargeBatch(long expenseHolderId, Date importTimeStamp) {

		ExpenseHolder expenseHolder = expenseHolderService.getExpenseHolderById(expenseHolderId);

		ChargeBatch chargeBatch = new ChargeBatch();
		chargeBatch.setExpenseHolder(expenseHolder);

		entityManager.persist(chargeBatch);

		setChargeBatchImportTime(chargeBatch.getChargeBatchId(), importTimeStamp);

		return chargeBatch;
	}

	@Transactional(readOnly = true)
	public Charge getChargeById(long chargeId) {
		return entityManager.find(Charge.class, chargeId);
	}


}
