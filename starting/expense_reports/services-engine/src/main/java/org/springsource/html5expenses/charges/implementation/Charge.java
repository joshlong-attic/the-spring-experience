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

package org.springsource.html5expenses.charges.implementation;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * a {@link Charge} is a free-standing entity that represents the boundry between an external credit card system and the
 * expense report system.  Essentially, charges may be selected and then added into {@link org.springsource.html5expenses.reports.implementation.ExpenseReport}s.
 * <p/>
 * Ideally, once an expense report's been successfully {@link org.springsource.html5expenses.reports.implementation.ExpenseReport#setClosed()}, then
 * the corresponding {@link Charge charge} would be {@link Charge#setPaid(boolean)} paid}.
 *
 * @author Josh Long
 */
@Entity
public class Charge {

	@Id @GeneratedValue
	private Long id;

	private boolean paid;

	private double amount;

	private String category;

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public double getAmount() {
		return amount;
	}

	public String getCategory() {
		return category;
	}

	public Long getId() {
		return id;
	}

	/**
	 * default ctor for Hibernate
	 */
	Charge() {
	}

	public Charge(double amt, String cat) {
		this.amount = amt;
		this.category = cat;
	}

	public Charge(Long id, double amount, String category) {
		this.id = id;
		this.amount = amount;
		this.category = category;
	}


}