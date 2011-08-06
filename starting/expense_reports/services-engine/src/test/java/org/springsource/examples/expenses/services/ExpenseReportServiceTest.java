package org.springsource.examples.expenses.services;

import org.apache.commons.lang.builder.ToStringBuilder;
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
import org.springsource.examples.expenses.model.ChargeBatch;
import org.springsource.examples.expenses.model.ExpenseHolder;
import org.springsource.examples.expenses.model.ExpenseReport;
import org.springsource.examples.expenses.model.ExpenseReportAuthorization;

import javax.inject.Inject;
import java.util.Date;


/**
 * put the {@link ExpenseReportService} through its paces.
 *
 * @author Josh Long
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServiceConfiguration.class})
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ExpenseReportServiceTest {

	@Inject private ChargeBatchService chargeBatchService;
	@Inject private ExpenseReportService expenseReportService;
	@Inject private ExpenseHolderService expenseHolderService;

	private ExpenseHolder top, topmiddle, bottommiddle, middle;
	private ExpenseHolder bottom;

	// charges imported from a credit card, no doubt
	private ChargeBatch batch;

	@Before
	public void before() throws Throwable {

		top = expenseHolderService.createExpenseHolder("Authorizer", "0", "authorizer0@email.com", "password");
		topmiddle = expenseHolderService.createExpenseHolder("Authorizer", "1", "authorizer1@email.com", "password");
		bottommiddle = expenseHolderService.createExpenseHolder("Authorizer", "2", "authorizer2@email.com", "password");
		bottom = expenseHolderService.createExpenseHolder("John", "Doe", "jdoe@email.com", "password");

		expenseHolderService.assignAuthorizingExpenseHolderToExpenseHolder(topmiddle.getExpenseHolderId(), top.getExpenseHolderId());
		expenseHolderService.assignAuthorizingExpenseHolderToExpenseHolder(bottommiddle.getExpenseHolderId(), topmiddle.getExpenseHolderId());
		expenseHolderService.assignAuthorizingExpenseHolderToExpenseHolder(bottom.getExpenseHolderId(), bottommiddle.getExpenseHolderId());

		// create an expense report
		batch = chargeBatchService.createChargeBatch(this.bottom.getExpenseHolderId(), new Date());
		chargeBatchService.createCharge(batch.getChargeBatchId(), 1.20, "a cappuccino");
		chargeBatchService.createCharge(batch.getChargeBatchId(), 26.32, "steak");

	}

	@Test
	public void testCreateExpenseReport() throws Throwable {

		ExpenseReport expenseReport = expenseReportService.createExpenseReportFromChargeBatch(this.bottom.getExpenseHolderId(), batch.getChargeBatchId());
		Assert.isTrue(expenseReport.getExpenseHolder().getExpenseHolderId() == bottom.getExpenseHolderId());

		expenseReportService.submitExpenseReportForApproval(expenseReport.getExpenseReportId());

		ExpenseReportAuthorization authorization;

		while ((authorization = expenseReportService.getNextExpenseReportAuthorization(expenseReport.getExpenseReportId())) != null) {
			System.out.println(ToStringBuilder.reflectionToString(authorization));
			expenseReportService.approveExpenseReportAuthorization(authorization.getExpenseReportAuthorizationId(), "well done!");
		}
	}

}
