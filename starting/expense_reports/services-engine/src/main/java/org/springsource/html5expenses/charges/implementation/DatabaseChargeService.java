/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
