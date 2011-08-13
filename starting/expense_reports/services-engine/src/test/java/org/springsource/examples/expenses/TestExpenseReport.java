package org.springsource.examples.expenses;


import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.springsource.examples.expenses.charges.ChargeBatch;
import org.springsource.examples.expenses.fs.ManagedFile;
import org.springsource.examples.expenses.fs.StorageNode;
import org.springsource.examples.expenses.reports.*;
import org.springsource.examples.expenses.users.CreditCard;
import org.springsource.examples.expenses.users.User;

import java.io.File;

public class TestExpenseReport {

	private LineItemValidationStrategy lineItemValidationStrategy = new DefaultLineItemValidationStrategy();

	private File desktop = new File(SystemUtils.getUserHome(), "Desktop");

	@Test
	public void testCreatingAnExpenseReport() throws Throwable {

		StorageNode node = new StorageNode(); // this definitely requires some sort of arbitration service


		User user = new User("Josh", "Long", "email@email.com", "password");
		CreditCard card = user.addCreditCard("Visa");

		ChargeBatch batch = new ChargeBatch();
		batch.addCharge(card, 90.00, "Apple MBP power adapter");
		batch.addCharge(card, 23.0, "sandwich");
		batch.addCharge(card, 300.00, "new fully functional, non-Apple computer");
		batch.addCharge(card, 1100.00, "new fully functional, Apple computer");

		ExpenseReport expenseReport = ExpenseReport.buildExpenseReportFromChargeBatch(lineItemValidationStrategy, user, batch);

		for (LineItem lineItem : expenseReport.getLineItems()) {
			File receipt = new File( desktop , "starbucks_receipt_" + System.currentTimeMillis() + ".jpg");
			ManagedFile file = new ManagedFile(node, receipt);
			lineItem.addAttachment("A description", file);
		}

		expenseReport.markAsFinal();


	}
}
