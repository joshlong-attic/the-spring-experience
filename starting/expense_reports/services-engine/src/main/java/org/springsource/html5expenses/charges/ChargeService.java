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

package org.springsource.html5expenses.charges;

import org.springsource.html5expenses.charges.implementation.Charge;

import java.util.List;

/**
 * Manages the pools of unreconciled charges.
 */

public interface ChargeService {
	Long createCharge( double amount, String category );
	org.springsource.html5expenses.charges.Charge  getCharge(Long chargeId);
	List<org.springsource.html5expenses.charges.Charge > getEligibleCharges();
	void markAsPaid(Long chargeId) ;
}
