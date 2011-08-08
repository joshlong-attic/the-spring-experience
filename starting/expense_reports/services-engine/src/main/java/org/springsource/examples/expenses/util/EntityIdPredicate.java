package org.springsource.examples.expenses.util;

import org.apache.commons.collections.Predicate;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Simple predicate based on Hibernate metadata. Works with either a
 * Hibernate {@link EntityManager entity manager} implementation or
 * a direct Hibernate {@link SessionFactory session factory}.
 *
 * @author Josh Long
 */
public class EntityIdPredicate implements Predicate {

	private BeanPropertyPredicate beanPropertyPredicateDelegate;

	public EntityIdPredicate(SessionFactory factory, Class<?> clzz, Object toMatch) {
		Assert.notNull(factory, "the 'entityManagerFactory' must not be null");
		Assert.notNull(clzz, "the class must not be null");
		Assert.notNull(toMatch, "the value to match must not be null");
		setup(factory, clzz, toMatch);
	}

	public EntityIdPredicate(EntityManagerFactory entityManagerFactory, Class<?> clzz, Object toMatch) {

		Assert.notNull(entityManagerFactory, "the 'entityManagerFactory' must not be null");
		Assert.notNull(clzz, "the class must not be null");
		Assert.notNull(toMatch, "the value to match must not be null");

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		if (entityManager instanceof HibernateEntityManager) {
			HibernateEntityManager hibernateEntityManager = (HibernateEntityManager) entityManager;
			SessionFactory session = hibernateEntityManager.getSession().getSessionFactory();
			setup(session, clzz, toMatch);
		} else {
			throw new RuntimeException("can't derive a handle to accquire the Hibernate metadata.");
		}
	}

	protected void setup(SessionFactory sessionFactory, Class<?> clzz, Object toMatch) {
		ClassMetadata metadata = sessionFactory.getClassMetadata(clzz);
		String propertyName = metadata.getIdentifierPropertyName();
		beanPropertyPredicateDelegate = new BeanPropertyPredicate(propertyName, toMatch);
	}

	@Override
	public boolean evaluate(Object object) {
		return beanPropertyPredicateDelegate.evaluate(object);
	}
}
