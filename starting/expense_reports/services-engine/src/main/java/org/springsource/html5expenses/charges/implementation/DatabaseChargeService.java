package org.springsource.html5expenses.charges.implementation;

import org.springsource.html5expenses.charges.Charge;
import org.springsource.html5expenses.charges.ChargeService;

import java.util.List;

public class DatabaseChargeService implements ChargeService{
	@Override
	public Charge getCharge(Long chargeId) {
		return null;
	}

	@Override
	public List<Charge> getEligibleCharges() {
		return null;
	}

	@Override
	public void markAsIneligible(Long chargeId) {
	}
}
