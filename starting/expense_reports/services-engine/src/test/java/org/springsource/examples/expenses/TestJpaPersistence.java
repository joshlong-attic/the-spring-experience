package org.springsource.examples.expenses;


import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springsource.html5expenses.charges.ChargeService;
import org.springsource.html5expenses.config.ServicesConfiguration;
import org.springsource.html5expenses.reports.Expense;
import org.springsource.html5expenses.reports.ExpenseReportingService;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServicesConfiguration.class})
public class TestJpaPersistence {

	@Inject
    private ExpenseReportingService expenseReport;

	@Inject
	private ChargeService chargeService ;

	private String purpose = "Palo Alto Face to Face";

	@Test
	public void testCreatingExpenseReports() throws Throwable {

		List<Long> charges = Arrays.asList(
			  chargeService.createCharge(242.23,"food"),
			  chargeService.createCharge(22.3,"coffee"));

		Long erId = expenseReport.createNewReport( this.purpose);
		Collection<Expense> expenseIds = expenseReport.addExpenses( erId,  charges);

		Assert.assertNotNull("the expense report ID can't be null", erId);



	}


}
