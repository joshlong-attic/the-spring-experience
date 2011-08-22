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

package org.springsource.examples.expenses;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springsource.html5expenses.charges.Charge;
import org.springsource.html5expenses.charges.ChargeService;
import org.springsource.html5expenses.config.ServicesConfiguration;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServicesConfiguration.class})
public class TestDatabaseChargeService {

	@Inject ChargeService chargeService;

	@Inject DataSource dataSource;

	double chargeAmt1 = 242.32, chargeAmt2 = 23.0;

	String chargeVendor1 = "food", chargeVendor2 = "coffee";

	List<Long> charges;

	@Before
	public void setup() throws Throwable {
		this.charges = Arrays.asList(chargeService.createCharge(chargeAmt1, chargeVendor1), chargeService.createCharge(chargeAmt2, chargeVendor2));
	}

	@After
	public void tearDown() throws Throwable {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		jdbcTemplate.execute("TRUNCATE TABLE CHARGE");
	}

	@Test
	public void testCreatingCharges() throws Throwable {
		Assert.assertTrue(charges.size() == 2);

		long c1Id = charges.get(0);
		long c2Id = charges.get(1);

		Charge c1 = chargeService.getCharge(c1Id);
		Charge c2 = chargeService.getCharge(c2Id);

		Assert.assertEquals(c1.getAmount(), chargeAmt1);
		Assert.assertEquals(c2.getAmount(), chargeAmt2);
		Assert.assertEquals(c1.getCategory(), chargeVendor1);
		Assert.assertEquals(c2.getCategory(), chargeVendor2);
		Assert.assertEquals(c1.getId(), (Long) c1Id);
		Assert.assertEquals(c2.getId(), (Long) c2Id);
	}

	@Test
	public void testFindingCharges() throws Throwable {
		Long chargeId = this.charges.iterator().next();
		Assert.assertEquals(chargeService.getCharge(chargeId).getId(), chargeId);
	}

	@Test
	public void testChargeReconciliation() throws Throwable {
		List<Charge> eligibleCharges = chargeService.getEligibleCharges();
		Assert.assertNotNull(eligibleCharges);
		Assert.assertTrue(eligibleCharges.size() == 2);
		for (Charge c : eligibleCharges) {
			chargeService.markAsPaid(c.getId());
		}
		Assert.assertTrue(chargeService.getEligibleCharges().size() == 0);
	}
}
