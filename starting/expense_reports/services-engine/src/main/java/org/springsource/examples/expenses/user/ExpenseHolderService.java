package org.springsource.examples.expenses.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Simple service that handles managing state associated with {@link ExpenseHolder} entities.
 *
 * @author Josh Long
 */
@Service
public class ExpenseHolderService {


	@PersistenceContext private EntityManager entityManager;

	@Transactional(readOnly = true)
	public ExpenseHolder login(String email, String pw) {
		String ehLoginQuery = String.format("select eh from %s eh where eh.email = :email and eh.password = :password", ExpenseHolder.class.getName());
		TypedQuery<ExpenseHolder> qu = entityManager.createQuery(ehLoginQuery, ExpenseHolder.class);
		qu.setParameter("email", email);
		qu.setParameter("password", pw);
		return qu.getSingleResult();
	}

	@Transactional
	public void assignAuthorizingExpenseHolderToExpenseHolder(long expenseHolderId, long authorizingExpenseHolderId) {
		ExpenseHolder eh = getExpenseHolderById(expenseHolderId);
		ExpenseHolder authorizingEh = getExpenseHolderById(authorizingExpenseHolderId);
		eh.setAuthorizingExpenseHolder(authorizingEh);
		authorizingEh.getExpenseHolders().add(eh);
		this.entityManager.merge(eh);
		this.entityManager.merge(authorizingEh);
	}

	@Transactional(readOnly = true)
	public ExpenseHolder getExpenseHolderById(long expenseHolderId) {
		return this.entityManager.find(ExpenseHolder.class, expenseHolderId);
	}

	@Transactional
	public ExpenseHolder createExpenseHolder(String firstName, String lastName, String email, String pw, double maxExpenditureAmt) {
		ExpenseHolder eh = new ExpenseHolder();
		eh.setFirstName(firstName);
		eh.setPassword(pw);
		eh.setUnjustifiedChargeAmountThreshold(maxExpenditureAmt);
		eh.setLastName(lastName);
		eh.setEmail(email);
		entityManager.persist(eh);
		return eh;
	}


}
