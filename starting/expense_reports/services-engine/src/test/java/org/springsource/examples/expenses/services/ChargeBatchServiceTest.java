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
import org.springsource.examples.expenses.*;
import org.springsource.examples.expenses.config.ServiceConfiguration;
import org.springsource.examples.expenses.user.ExpenseHolder;
import org.springsource.examples.expenses.user.ExpenseHolderService;
import org.springsource.examples.expenses.util.EntityIdPredicate;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.Set;

/**
 * put the {@link org.springsource.examples.expenses.ChargeBatch} service through its paces.
 *
 * @author Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServiceConfiguration.class})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ChargeBatchServiceTest {

	@Inject ExpenseHolderService expenseHolderService;
	@Inject ChargeBatchService chargeBatchService;
	@Inject EntityManagerFactory entityManagerFactory;

	private ExpenseHolder expenseHolder;

	@Before
	public void before() throws Throwable {
		expenseHolder = expenseHolderService.createExpenseHolder("josh", "long", "email@email.com", "pw",25);
	}

	@Test
	public void testCreatingChargeBatches() throws Throwable {

		ChargeBatch batch;

		batch = chargeBatchService.createChargeBatch(this.expenseHolder.getExpenseHolderId(), new Date());

		Assert.assertEquals(batch.getExpenseHolder().getExpenseHolderId(), expenseHolder.getExpenseHolderId());

		Charge cappuccino = chargeBatchService.createCharge(batch.getChargeBatchId(), 1.20, "a cappuccino");
		Charge steak = chargeBatchService.createCharge(batch.getChargeBatchId(), 26.32, "steak");

		batch = chargeBatchService.getChargeBatchById(batch.getChargeBatchId());

		Set<Charge> charges = chargeBatchService.getChargeBatchCharges(batch.getChargeBatchId());

		Assert.assertTrue(charges.size() == 2);
		Assert.assertTrue(CollectionUtils.exists(charges, new EntityIdPredicate(entityManagerFactory, Charge.class, cappuccino.getChargeId())));
		Assert.assertTrue(CollectionUtils.exists(charges, new EntityIdPredicate(entityManagerFactory, Charge.class, steak.getChargeId())));


	}
}
