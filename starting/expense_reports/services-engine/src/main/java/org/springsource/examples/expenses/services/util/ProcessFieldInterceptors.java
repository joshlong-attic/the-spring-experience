package org.springsource.examples.expenses.services.util;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;


/**
 *
 * Simple Hibernate {@link org.hibernate.Interceptor} to ensure that all entities have their 'dateModified' and 'dateCreated' fields appropriately updated
 *
 * @author Josh Long
 */
public class ProcessFieldInterceptors extends EmptyInterceptor   {

	@Override
	public boolean onFlushDirty(Object o, Serializable serializable, Object[] objects, Object[] objects1, String[] strings, Type[] types) {
		setValue(objects, strings, "dateModified", new Date());
		return true;
	}

	@Override
	public boolean onSave(Object o, Serializable serializable, Object[] objects, String[] strings, Type[] types) {
		Date now = new Date();
		setValue(objects, strings, "dateCreated", now);
		setValue(objects, strings, "dateModified", now);
		return true;
	}

	private void setValue(Object[] state, String[] props, String propertyToSet, Object val) {
		int indxOfPropertyNameInPropertyNamesArr = Arrays.asList(props).indexOf(propertyToSet);
		if (indxOfPropertyNameInPropertyNamesArr >= 0) {
			state[indxOfPropertyNameInPropertyNamesArr] = val;
		}
	}

}
