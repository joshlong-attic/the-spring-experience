package org.springsource.examples.expenses.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Simple predicate to find objects with a specific JavaBeans property on them
 *
 * @author Josh Long
 */
public class BeanPropertyPredicate implements Predicate {

	private Log log = LogFactory.getLog(getClass());

	private String propertyName;

	private Object toMatch;

	public BeanPropertyPredicate(String propertyName, Object toMatch) {
		this.propertyName = propertyName;
		this.toMatch = toMatch;
	}

	protected void error(Exception ex) {
		if (log.isErrorEnabled()) {
			log.error(ex);
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean evaluate(Object object) {
		Object value = null;
		try {
			value = PropertyUtils.getProperty(object, this.propertyName);
		} catch (IllegalAccessException e) {
			error(e);
		} catch (InvocationTargetException e) {
			error(e);
		} catch (NoSuchMethodException e) {
			error(e);
		}

		if (value != null) {
			if (value == toMatch || value.equals(toMatch)) {
				return true;
			}
		}

		return false;
	}
}
