package org.springsource.examples.expenses;


import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springsource.html5expenses.charges.Charge;
import org.springsource.html5expenses.charges.ChargeService;
import org.springsource.html5expenses.config.ServicesConfiguration;
import org.springsource.html5expenses.reports.Expense;
import org.springsource.html5expenses.reports.ExpenseReport;
import org.springsource.html5expenses.reports.ExpenseReportingService;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServicesConfiguration.class})
public class TestDatabaseExpenseReportingService {

	@Inject ExpenseReportingService reportingService;

	@Inject ChargeService chargeService;

	double chargeAmt1 = 242.32, chargeAmt2 = 23.0;

	String chargeVendor1 = "food", chargeVendor2 = "coffee";

	String purpose = "Palo Alto Face to Face";

	List<Long> charges;

	@Inject DataSource dataSource ;

	@Before
	public void setup() throws Throwable {
		this.charges = Arrays.asList(chargeService.createCharge(chargeAmt1, chargeVendor1), chargeService.createCharge(chargeAmt2, chargeVendor2));
	}

	@Test
	public void testCreatingCharges() throws Throwable {
		Assert.assertTrue(charges.size() == 2);

		long c1Id = charges.get(0);
		long c2Id = charges.get(1);

		Charge c1 = chargeService.getCharge(c1Id);
		Charge c2 = chargeService.getCharge(c2Id);

		Assert.assertEquals(c1.getAmount(), chargeAmt1);
		Assert.assertEquals(c2.getAmount(), chargeAmt2);
		Assert.assertEquals(c1.getCategory(), chargeVendor1);
		Assert.assertEquals(c2.getCategory(), chargeVendor2);
		Assert.assertEquals(c1.getId(), (Long) c1Id);
		Assert.assertEquals(c2.getId(), (Long) c2Id);
	}

	@Test
	public void testAddingChargesToReport() throws Throwable {
		Long erId = reportingService.createNewReport(this.purpose);
		Assert.assertNotNull(erId);
		List<Expense> expenses = reportingService.addExpenses(erId, this.charges);
		Assert.assertEquals(expenses.size(), this.charges.size());
		List<ExpenseReport> er = reportingService.getOpenReports();
		Assert.assertEquals(er.size(), 1);
		ExpenseReport firstReport = er.iterator().next();
		Assert.assertNotNull(firstReport.getId());
		Assert.assertEquals(firstReport.getExpenses().size(), this.charges.size());

	}

/*

	@Test
	public void testAddingChargesToReport() throws Throwable {

		Long erId = expenseReport.createNewReport(this.purpose);
		Collection<Expense> expenseIds = expenseReport.addExpenses(erId, charges);

		Assert.assertNotNull("the expense report ID can't be null", erId);


	}
*/


}
