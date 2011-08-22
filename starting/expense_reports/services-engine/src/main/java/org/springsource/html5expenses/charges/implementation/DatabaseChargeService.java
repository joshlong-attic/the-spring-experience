package org.springsource.html5expenses.charges.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.html5expenses.charges.ChargeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseChargeService implements ChargeService {

	@PersistenceContext
	private EntityManager entityManager;

	private String eligibleChargesQuery = String.format("SELECT c FROM %s c WHERE c.paid = FALSE", Charge.class.getName());

	@Transactional
	public Long createCharge(double amount, String category) {
		Charge c = new Charge(amount, category);
		entityManager.persist(c);
		return c.getId();
	}

	@Transactional
	public org.springsource.html5expenses.charges.Charge getCharge(Long chargeId) {
		Charge c = entityManager.find(Charge.class, chargeId);
		return fromCharge(c);
	}

	@Transactional
	public List<org.springsource.html5expenses.charges.Charge> getEligibleCharges() {
		List<Charge> charges = entityManager.createQuery(this.eligibleChargesQuery, Charge.class).getResultList();
		return fromCharges(charges);
	}

	@Transactional
	public void markAsPaid(Long chargeId) {
		Charge charge = entityManager.find(Charge.class, chargeId);
		charge.setPaid(true);
		entityManager.merge(charge);
	}

	private List<org.springsource.html5expenses.charges.Charge> fromCharges(List<Charge> charges) {
		List<org.springsource.html5expenses.charges.Charge> chargeList = new ArrayList<org.springsource.html5expenses.charges.Charge>();
		for (Charge c : charges) {
			chargeList.add(fromCharge(c));
		}
		return chargeList;
	}

	private org.springsource.html5expenses.charges.Charge fromCharge(Charge ic) {
		org.springsource.html5expenses.charges.Charge oc = new org.springsource.html5expenses.charges.Charge();
		oc.setAmount(ic.getAmount());
		oc.setCategory(ic.getCategory());
		oc.setId(ic.getId());
		oc.setUserId(ic.getUserId());
		oc.setPaid(ic.isPaid());
		return oc;
	}
}
