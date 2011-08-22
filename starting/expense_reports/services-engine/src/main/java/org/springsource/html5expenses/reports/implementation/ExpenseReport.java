/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springsource.html5expenses.reports.implementation;

import org.springsource.html5expenses.charges.implementation.Charge;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * aggregate for reconciled {@link Charge}s, which are called {@link Expense}.
 *
 * @author Josh Long
 */
@Entity
public class ExpenseReport {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String purpose;

	@OneToMany(mappedBy = "expenseReport")
	private Set<Expense> expenses = new HashSet<Expense>();

	@Enumerated(EnumType.STRING)
	private ExpenseReportState state = ExpenseReportState.OPEN;

	@Transient
	private ExpenseValidationStrategy expenseValidationStrategy = new DefaultExpenseValidationStrategy();

	public Set<Expense> getExpenses() {
		return expenses;
	}

	public ExpenseReportState getState() {
		return state;
	}

	public Expense addExpense(long chargeId, double chargeAmt, String category) {

		if (!this.state.equals(ExpenseReportState.OPEN)) {
			throw new IllegalStateException("you can't add expenses to a closed expense report.");
		}
		Expense item = new Expense(chargeId, chargeAmt);
		item.setCategory(category);
		item.setExpenseReport(this);
		getExpenses().add(item);
		return item;
	}

	public boolean validate() {
		Collection<Expense> expenseCollection = getExpenses();
		boolean valid = expenseCollection.size() > 0;
		for (Expense expense : expenseCollection) {
			if (!expenseValidationStrategy.validate(expense)) {
				valid = false;
			}
		}
		return valid;
	}

	public void setPendingReview() {
		if (!validate()) {
			throw new IllegalStateException("the report's not valid and can't be filed.");
		}
		this.state = ExpenseReportState.IN_REVIEW;
	}

	public void setOpen() {
		boolean inValidState = state.equals(ExpenseReportState.IN_REVIEW) || state.equals(ExpenseReportState.OPEN);
		if (!inValidState) {
			throw new IllegalStateException("a report can only be opened if it's already opened or if it's been rejected.");
		}
		this.state = ExpenseReportState.OPEN;
	}

	public void setClosed() {
		if (isFlagged()) {
			throw new IllegalArgumentException("the report's been flagged and can't be closed");
		}
		if (!validate()) {
			throw new IllegalStateException("the report's not valid and can't be closed.");
		}

		this.state = ExpenseReportState.CLOSED;
	}

	public boolean isFlagged() {
		for (Expense ex : getExpenses()) {
			if (ex.isFlagged()) {
				return true;
			}
		}
		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


}