package org.springsource.examples.expenses.services;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.expenses.charges.Charge;
import org.springsource.examples.expenses.charges.ChargeBatch;
import org.springsource.examples.expenses.charges.ChargeBatchService;
import org.springsource.examples.expenses.config.ServiceConfiguration;
import org.springsource.examples.expenses.users.User;
import org.springsource.examples.expenses.users.UserService;
import org.springsource.examples.expenses.util.predicates.EntityIdPredicate;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.Set;

/**
 * put the {@link org.springsource.examples.expenses.charges.ChargeBatch} service through its paces.
 *
 * @author Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServiceConfiguration.class})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ChargeBatchServiceTest {

	@Inject UserService expenseHolderService;
	@Inject ChargeBatchService chargeBatchService;
	@Inject EntityManagerFactory entityManagerFactory;

	private User user;

	@Before
	public void before() throws Throwable {
		user = expenseHolderService.createExpenseHolder("josh", "long", "email@email.com", "pw",25);
	}

	@Test
	public void testCreatingChargeBatches() throws Throwable {

		ChargeBatch batch;

		batch = chargeBatchService.createChargeBatch(this.user.getUserId(), new Date());

		Assert.assertEquals(batch.getUser().getUserId(), user.getUserId());

		Charge cappuccino = chargeBatchService.createCharge(batch.getChargeBatchId(), 1.20, "a cappuccino");
		Charge steak = chargeBatchService.createCharge(batch.getChargeBatchId(), 26.32, "steak");

		batch = chargeBatchService.getChargeBatchById(batch.getChargeBatchId());

		Set<Charge> charges = chargeBatchService.getChargeBatchCharges(batch.getChargeBatchId());

		Assert.assertTrue(charges.size() == 2);
		Assert.assertTrue(CollectionUtils.exists(charges, new EntityIdPredicate(entityManagerFactory, Charge.class, cappuccino.getChargeId())));
		Assert.assertTrue(CollectionUtils.exists(charges, new EntityIdPredicate(entityManagerFactory, Charge.class, steak.getChargeId())));


	}
}
