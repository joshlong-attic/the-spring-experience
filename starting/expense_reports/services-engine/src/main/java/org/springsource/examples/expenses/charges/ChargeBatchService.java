package org.springsource.examples.expenses.charges;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.expenses.users.User;
import org.springsource.examples.expenses.users.UserService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.Set;

/**
 * service to manage {@link Charge charges} and {@link ChargeBatch charge batches}.
 * <p/>
 * <p/>
 * NB: {@link Charge}s are assumed to be in a single, normalized currency. Presumably, they will be normalized when imported.
 *
 * @author Josh Long
 */
@Service
public class ChargeBatchService {

	@PersistenceContext private EntityManager entityManager;

	@Inject private UserService expenseHolderService;

	/**
	 * creates a charge for a charge batch. A single charge typically corresponds to a charge on a credit card.
	 * <p/>
	 * Perhaps there are other sources that can be expensed? Perhaps companies give their employees company checkbooks?
	 * <p/>
	 * This might then correspond to a charge written against a company check. Either way, it
	 * represents a unique, isolated, quantifiable charge (perhaps with a description from the vendor, e.g., "Starbucks ventura blvd").
	 *
	 * @param chargeBatchId the id of the charge batch
	 * @param chargeAmt     the liability amount that this charge represents to the {@link org.springsource.examples.expenses.users.User}
	 * @param description   a description of the charge (as perhaps might appear on the credit card company's line items). Example: "starbucks ventura blvd 5555"
	 * @return a {@link Charge} object (that has been attached the appropriate {@link ChargeBatch}
	 */
	@Transactional
	public Charge createCharge(long chargeBatchId, double chargeAmt, String description) {
		ChargeBatch batch = getChargeBatchById(chargeBatchId);
		Charge charge = new Charge();
		charge.setChargeAmount(chargeAmt);
		charge.setChargeBatch(batch);
		charge.setDescription(description);
		batch.getCharges().add(charge);
		entityManager.persist(charge);
		entityManager.merge(batch);
		return charge;
	}

	@Transactional(readOnly = true)
	public Set<Charge> getChargeBatchCharges(long chargeBatchId) {
		ChargeBatch batch = getChargeBatchById(chargeBatchId);
		Set<Charge> charges = batch.getCharges();
		Hibernate.initialize(charges);
		return charges;
	}


	@Transactional(readOnly = true)
	public ChargeBatch getChargeBatchById(long chargeBatchId) {
		return entityManager.find(ChargeBatch.class, chargeBatchId);
	}

	/**
	 * Umbrella object. Represents a grouping of a specific time, as well as a specific {@link org.springsource.examples.expenses.users.User}.
	 * <p/>
	 * You might imagine that a system imports {@link Charge}s as part of a {@link ChargeBatch} every day at midnight.
	 * <p/>
	 * This simply provides a convenient way to group chronic imports.
	 * <p/>
	 * The charge batch may or may not be an abstraction that the users of the system needs to know about.
	 *
	 * @param expenseHolderId the id of the expense holder
	 * @param importTimeStamp when this batch was created.
	 * @return a {@link ChargeBatch} collecting all the charges in it.
	 */
	@Transactional
	public ChargeBatch createChargeBatch(long expenseHolderId, Date importTimeStamp) {

		User user = expenseHolderService.getExpenseHolderById(expenseHolderId);

		ChargeBatch chargeBatch = new ChargeBatch();
		chargeBatch.setUser(user);
		chargeBatch.setImportTime(importTimeStamp);

		entityManager.persist(chargeBatch);

		return chargeBatch;
	}

	@Transactional(readOnly = true)
	public Charge getChargeById(long chargeId) {
		return entityManager.find(Charge.class, chargeId);
	}


}
