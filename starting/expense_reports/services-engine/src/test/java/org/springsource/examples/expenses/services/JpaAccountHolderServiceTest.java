package org.springsource.examples.expenses.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springsource.examples.expenses.config.ServiceConfiguration;
import org.springsource.examples.expenses.model.ExpenseHolder;

import javax.inject.Inject;

/**
 * unit test for the {@link JpaExpenseHolderService}.
 *
 * @author Josh Long
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServiceConfiguration.class})
public class JpaAccountHolderServiceTest {

	@Inject JpaExpenseHolderService expenseHolderService;


	@Before
	public void before() throws Throwable {
	}


	@Test
	public void testAuditing() throws Throwable {
		ExpenseHolder eh = this.expenseHolderService.createExpenseHolder("Josh", "Long", "josh.long@springsource.com", "password");
		Assert.assertNotNull(eh.getDateCreated() != null);
		Assert.assertNotNull(eh.getDateModified() != null);
		Assert.assertEquals(eh.getDateModified(), eh.getDateCreated());
	}

	@Test
	public void testAccountHolderCreation() throws Throwable {
		ExpenseHolder eh = this.expenseHolderService.createExpenseHolder("Josh", "Long", "josh.long@springsource.com", "password");
		Assert.assertNotNull(eh);
		Assert.assertTrue(eh.getExpenseHolderId() > 0);

	}
}
