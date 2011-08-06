package org.springsource.examples.expenses.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.expenses.model.Charge;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * service to manage {@link org.springsource.examples.expenses.model.Charge charges} and {@link org.springsource.examples.expenses.model.ChargeBatch charge batches}.
 *
 * @author Josh Long
 */
@Service
public class ChargeBatchService {

	@PersistenceContext private EntityManager entityManager;

	@Transactional(readOnly = true)
	public Charge getChargeById(long chargeId) {
		return entityManager.find(Charge.class, chargeId);
	}


}
