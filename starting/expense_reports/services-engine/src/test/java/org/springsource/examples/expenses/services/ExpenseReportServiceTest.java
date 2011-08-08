package org.springsource.examples.expenses.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.expenses.charges.ChargeBatch;
import org.springsource.examples.expenses.charges.ChargeBatchService;
import org.springsource.examples.expenses.config.ServiceConfiguration;
import org.springsource.examples.expenses.reports.*;
import org.springsource.examples.expenses.fs.ManagedFile;
import org.springsource.examples.expenses.fs.ManagedFileMountPrefix;
import org.springsource.examples.expenses.fs.ManagedFileService;
import org.springsource.examples.expenses.users.ExpenseHolder;
import org.springsource.examples.expenses.users.ExpenseHolderService;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Date;


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


	@Inject private ManagedFileService managedFileService;
	@Inject private ChargeBatchService chargeBatchService;
	@Inject private ExpenseReportService expenseReportService;
	@Inject private ExpenseHolderService expenseHolderService;

	private ExpenseHolder top, topMiddle, bottomMiddle, bottom;

	private ChargeBatch batch;

	private double maxAmount = 25;

	@Before
	public void before() throws Throwable {

		top = expenseHolderService.createExpenseHolder("Authorizer", "0", "authorizer0@email.com", "password", maxAmount);
		topMiddle = expenseHolderService.createExpenseHolder("Authorizer", "1", "authorizer1@email.com", "password", maxAmount);
		bottomMiddle = expenseHolderService.createExpenseHolder("Authorizer", "2", "authorizer2@email.com", "password", maxAmount);
		bottom = expenseHolderService.createExpenseHolder("John", "Doe", "jdoe@email.com", "password", maxAmount);

		expenseHolderService.assignAuthorizingExpenseHolderToExpenseHolder(topMiddle.getExpenseHolderId(), top.getExpenseHolderId());
		expenseHolderService.assignAuthorizingExpenseHolderToExpenseHolder(bottomMiddle.getExpenseHolderId(), topMiddle.getExpenseHolderId());
		expenseHolderService.assignAuthorizingExpenseHolderToExpenseHolder(bottom.getExpenseHolderId(), bottomMiddle.getExpenseHolderId());

		// create an expense report
		batch = chargeBatchService.createChargeBatch(this.bottom.getExpenseHolderId(), new Date());
		chargeBatchService.createCharge(batch.getChargeBatchId(), 1.20, "a cappuccino");
		chargeBatchService.createCharge(batch.getChargeBatchId(), 26.32, "steak");
	}

	@Test
	public void testCreateExpenseReport() throws Throwable {

		final ExpenseReport expenseReport = expenseReportService.createExpenseReportFromChargeBatch(this.bottom.getExpenseHolderId(), batch.getChargeBatchId());

		Assert.assertTrue(expenseReport.getExpenseHolder().getExpenseHolderId() == bottom.getExpenseHolderId());

		Collection<ExpenseReportLine> lineItems = expenseReportService.getExpenseReportLines(expenseReport.getExpenseReportId());
		for (ExpenseReportLine el : lineItems) {
			Assert.assertTrue((el.isRequiresReceipt() && el.getCharge().getChargeAmount() > maxAmount) || !el.isRequiresReceipt());
			ManagedFile jpgForReceiptScan = managedFileService.createManagedFile("cheese_cake_factory_" + (Math.random() * 100) + ".jpg", "jpg", ManagedFileMountPrefix.DEFAULT, 1024 * ((Math.random() * 200) + 2), 0);
			Attachment attachment = expenseReportService.createExpenseReportLineAttachment(el.getExpenseReportLineId(), jpgForReceiptScan.getManagedFileId(), "this is a bit blurred because it was taken at an angle from my camera phone.");
		}
		expenseReportService.submitExpenseReportForApproval(expenseReport.getExpenseReportId());

		ExpenseReportAuthorization authorization;
		int depth = 0;
		while ((authorization = expenseReportService.getNextExpenseReportAuthorization(expenseReport.getExpenseReportId())) != null) {

			if (depth == 0) {
				Assert.assertTrue(authorization.getAuthorizingExpenseHolder().getExpenseHolderId() == bottomMiddle.getExpenseHolderId());
			}
			if (depth == 1) {
				Assert.assertTrue(authorization.getAuthorizingExpenseHolder().getExpenseHolderId() == topMiddle.getExpenseHolderId());
			}
			if (depth == 2) {
				Assert.assertTrue(authorization.getAuthorizingExpenseHolder().getExpenseHolderId() == top.getExpenseHolderId());
			}
			expenseReportService.approveExpenseReportAuthorization(authorization.getExpenseReportAuthorizationId(), "well done!");

			depth += 1;
		}
	}
}
