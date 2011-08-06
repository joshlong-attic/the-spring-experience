package org.springsource.examples.expenses.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.expenses.config.ServiceConfiguration;
import org.springsource.examples.expenses.model.ExpenseHolder;

import javax.inject.Inject;

/**
 * unit test for the {@link ExpenseHolderService}.
 *
 * @author Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServiceConfiguration.class})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AccountHolderServiceTest {

	@Inject private ExpenseHolderService expenseHolderService;

	@Test
	public void testAuditing() throws Throwable {
		ExpenseHolder eh = expenseHolderService.createExpenseHolder("Josh", "Long", "josh.long@springsource.com", "password");
		Assert.assertNotNull(eh.getDateCreated() != null);
		Assert.assertNotNull(eh.getDateModified() != null);
		Assert.assertEquals(eh.getDateModified(), eh.getDateCreated());
	}

	@Test
	public void testAccountHolderCreation() throws Throwable {
		ExpenseHolder eh = expenseHolderService.createExpenseHolder("Josh", "Long", "josh.long@springsource.com", "password");
		Assert.assertNotNull(eh);
		Assert.assertTrue(eh.getExpenseHolderId() > 0);
	}

	@Test
	public void testLogin() throws Throwable {
		String email = "john.doe@email.com";
		String password = "password";
		ExpenseHolder expenseHolder = this.expenseHolderService.createExpenseHolder("John", "Doe", email, password);
		Assert.assertNotNull("the expenseHolder is not null", expenseHolder);
		ExpenseHolder sameOne = expenseHolderService.login(email, password);
		Assert.assertEquals(sameOne.getExpenseHolderId(), expenseHolder.getExpenseHolderId());
	}

	@Test
	public void testAuthorizingExpenseHolderCreation() throws Exception {
		ExpenseHolder eh = expenseHolderService.createExpenseHolder("John", "Doe", "john.doe@email.com", "password");
		ExpenseHolder authorizingEh = expenseHolderService.createExpenseHolder("Jane", "Doe", "jane.doe@email.com", "password");
		expenseHolderService.assignAuthorizingExpenseHolderToExpenseHolder(eh.getExpenseHolderId(), authorizingEh.getExpenseHolderId());
		long authorizingEhId = expenseHolderService.getExpenseHolderById(eh.getExpenseHolderId()).getAuthorizingExpenseHolder().getExpenseHolderId();
		Assert.assertTrue(authorizingEhId > 0);
		Assert.assertEquals(authorizingEhId, authorizingEh.getExpenseHolderId());
	}
}
