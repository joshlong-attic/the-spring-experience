package org.springsource.examples.expenses.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.expenses.users.User;
import org.springsource.examples.expenses.users.UserService;
import org.springsource.examples.expenses.config.ServiceConfiguration;

import javax.inject.Inject;

/**
 * unit test for the {@link org.springsource.examples.expenses.users.UserService}.
 *
 * @author Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServiceConfiguration.class})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ExpenseHolderServiceTest {

	@Inject private UserService expenseHolderService;

	private double maxExpenditureWithoutJustification = 25.0 ;
	@Test
	public void testAuditing() throws Throwable {
		User eh = expenseHolderService.createExpenseHolder("Josh", "Long", "josh.long@springsource.com", "password",maxExpenditureWithoutJustification);

	}

	@Test
	public void testAccountHolderCreation() throws Throwable {
		User eh = expenseHolderService.createExpenseHolder("Josh", "Long", "josh.long@springsource.com", "password",maxExpenditureWithoutJustification);
		Assert.assertNotNull(eh);
		Assert.assertTrue(eh.getUserId() > 0);
	}

	@Test
	public void testLogin() throws Throwable {
		String email = "john.doe@email.com";
		String password = "password";
		User user = this.expenseHolderService.createExpenseHolder("John", "Doe", email, password,maxExpenditureWithoutJustification);
		Assert.assertNotNull("the user is not null", user);
		User sameOne = expenseHolderService.login(email, password);
		Assert.assertEquals(sameOne.getUserId(), user.getUserId());
	}

	@Test
	public void testAuthorizingExpenseHolderCreation() throws Exception {
		User eh = expenseHolderService.createExpenseHolder("John", "Doe", "john.doe@email.com", "password",maxExpenditureWithoutJustification);
		User authorizingEh = expenseHolderService.createExpenseHolder("Jane", "Doe", "jane.doe@email.com", "password",maxExpenditureWithoutJustification);
		expenseHolderService.assignAuthorizingExpenseHolderToExpenseHolder(eh.getUserId(), authorizingEh.getUserId());
		long authorizingEhId = expenseHolderService.getExpenseHolderById(eh.getUserId()).getAuthorizingUser().getUserId();
		Assert.assertTrue(authorizingEhId > 0);
		Assert.assertEquals(authorizingEhId, authorizingEh.getUserId());
	}
}
