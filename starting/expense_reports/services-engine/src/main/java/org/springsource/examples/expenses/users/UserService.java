package org.springsource.examples.expenses.users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Simple service that handles managing state associated with {@link User} entities.
 *
 * @author Josh Long
 */
@Service
public class UserService {


	@PersistenceContext private EntityManager entityManager;

	/**
	 * TODO make this talk to Spring Security
	 *
	 * @param email
	 * @param pw
	 * @return
	 */
	@Transactional(readOnly = true)
	public User login(String email, String pw) {
		String loginQuery = String.format("select eh from %s eh where eh.email = :email and eh.password = :password", User.class.getName());
		TypedQuery<User> qu = entityManager.createQuery(loginQuery, User.class);
		qu.setParameter("email", email);
		qu.setParameter("password", pw);
		return qu.getSingleResult();
	}

	@Transactional
	public void assignAuthorizingExpenseHolderToExpenseHolder(long expenseHolderId, long authorizingExpenseHolderId) {
		User eh = getExpenseHolderById(expenseHolderId);
		User authorizingEh = getExpenseHolderById(authorizingExpenseHolderId);
		eh.setAuthorizingUser(authorizingEh);
		authorizingEh.getUsers().add(eh);
		this.entityManager.merge(eh);
		this.entityManager.merge(authorizingEh);
	}

	@Transactional(readOnly = true)
	public User getExpenseHolderById(long expenseHolderId) {
		return this.entityManager.find(User.class, expenseHolderId);
	}

	@Transactional
	public User createExpenseHolder(String firstName, String lastName, String email, String pw, double maxExpenditureAmt) {
		User eh = new User();
		eh.setFirstName(firstName);
		eh.setPassword(pw);
		eh.setExpensableAmountWithoutReceipt(maxExpenditureAmt);
		eh.setLastName(lastName);
		eh.setEmail(email);
		entityManager.persist(eh);
		return eh;
	}


}
