package org.springsource.html5expenses.reports.implementation;

import org.springsource.html5expenses.charges.Charge;
import org.springsource.html5expenses.reports.*;
import org.springsource.html5expenses.reports.Expense;
import org.springsource.html5expenses.reports.ExpenseReport;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseExpenseReportingService implements ExpenseReportingService {


	private Expense buildExpenseFrom(org.springsource.html5expenses.reports.implementation.Expense ex){
		return new Expense( ex.getCategory(), ex.getAmount(),ex.getChargeId(),ex.getReceiptFileId(), ex.isFlagged(),ex.getFlag());
	}

	private Set<Expense> buildExpensesFrom(Set<org.springsource.html5expenses.reports.implementation.Expense> expenses){
		Set<Expense > result  =  new HashSet<Expense>() ;
		for(org.springsource.html5expenses.reports.implementation.Expense e : expenses)
			result.add( buildExpenseFrom(e)) ;
		return result ;
	}

	private ExpenseReport buildExpenseReportFrom(org.springsource.html5expenses.reports.implementation.ExpenseReport er) {
		ExpenseReport clientEr = new ExpenseReport();
		clientEr.setId(er.getId());
		clientEr.setPurpose( er.getPurpose());
		clientEr.setExpenses( buildExpensesFrom(er.getExpenses()) );

		return clientEr ;
	}


	@Override
	public Long createNewReport(String purpose) {
		return null;
	}

	@Override
	public List<Charge> getEligibleCharges() {
		return null;
	}

	@Override
	public List<Expense> addExpenses(Long reportId, List<Long> chargeIds) {
		return null;
	}

	@Override
	public Long addReceipt(Long expenseId, byte[] receiptBytes) {
		return null;
	}

	@Override
	public FilingResult fileReport(Long reportId) {
		return null;
	}

	@Override
	public List<ExpenseReport> getOpenReports() {
		return null;
	}
}
