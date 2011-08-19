package org.springsource.html5expenses.charges.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.html5expenses.charges.Charge;
import org.springsource.html5expenses.charges.ChargeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class DatabaseChargeService implements ChargeService {

	@PersistenceContext private EntityManager entityManager ;

	@Transactional
	public Long createCharge(double amount, String category) {
		Charge c = new Charge( amount, category);
		entityManager.persist(c);
		return c.getId();
	}

	@Transactional
	public Charge getCharge(Long chargeId) {
		return entityManager.find(Charge.class,chargeId);
	}

	@Transactional
	public List<Charge> getEligibleCharges() {
		return null;
	}

	@Transactional
	public void markAsIneligible(Long chargeId) {
		Charge charge= getCharge(chargeId);
		charge.setReconciled();
		entityManager.merge(charge);
	}
}
