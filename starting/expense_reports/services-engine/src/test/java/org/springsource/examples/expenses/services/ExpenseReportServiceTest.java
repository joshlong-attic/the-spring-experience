package org.springsource.examples.expenses.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springsource.examples.expenses.config.ServiceConfiguration;
import org.springsource.examples.expenses.model.ExpenseHolder;
import org.springsource.examples.expenses.model.ExpenseReport;

import javax.inject.Inject;


/**
 * put the {@link ExpenseReportService} through its paces.
 *
 * @author Josh Long
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServiceConfiguration.class})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ExpenseReportServiceTest {

	@Inject private ExpenseReportService expenseReportService;
   	@Inject private ExpenseHolderService expenseHolderService;

	private ExpenseHolder eh  ;

	@Before
	public void before() throws Throwable {
		this.eh = expenseHolderService.createExpenseHolder("Josh", "Long", "josh.long@springsource.com", "password");
	}

	@Test
	public void testCreateExpenseReport() throws Throwable  {

		ExpenseReport expenseReport = expenseReportService.createExpenseReport( this.eh.getExpenseHolderId());
		Assert.isTrue(expenseReport.getExpenseHolder().getExpenseHolderId() == eh.getExpenseHolderId());


	}

}
