package org.springsource.examples.expenses.charges;

import java.util.Collection;

/**
 * Manages the pools of unreconciled charges.
 */

public interface ChargeService {

	Collection<Charge> getUnReconciledCharges(/*String userId*/);

	void markChargeAsReconciled(Long chargeId) ;
}
