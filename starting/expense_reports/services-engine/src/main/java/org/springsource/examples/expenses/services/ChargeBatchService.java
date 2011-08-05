package org.springsource.examples.expenses.services;

import org.springsource.examples.expenses.model.Charge;

/**
 *
 * service to manage {@link org.springsource.examples.expenses.model.Charge charges} and {@link org.springsource.examples.expenses.model.ChargeBatch charge batches}.
 *
 * @author Josh Long
 */
public interface ChargeBatchService {

	Charge getChargeById(long chargeId) ;


}
