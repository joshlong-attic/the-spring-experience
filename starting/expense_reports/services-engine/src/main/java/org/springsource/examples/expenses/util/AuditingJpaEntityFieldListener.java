package org.springsource.examples.expenses.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;


/**
 * Simple Hibernate {@link org.hibernate.Interceptor} to ensure that all entities have their 'dateModified'
 * and 'dateCreated' fields appropriately updated. Useful
 * for auditing common in SOX environments.
 *
 * @author Josh Long
 */
public class AuditingJpaEntityFieldListener {

	private Log log = LogFactory.getLog(getClass());

	private String dateModified = "dateModified";

	private String dateCreated = "dateCreated";

	@PrePersist
	public void onPrePersist(Object object) {
		Date d = new Date();
		setProperty(object, this.dateCreated, d);
		setProperty(object, this.dateModified, d);
	}

	@PreUpdate
	public void onPreUpdate(Object object) {
		Date d = new Date();
		setProperty(object, this.dateModified, d);
	}

	protected void setProperty(Object obj, String propertyName, Date date) {
		Assert.notNull(obj, "the object can't be null");
		try {
			BeanUtils.setProperty(obj, propertyName, date);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("Error when trying to set a property on an entity", e);
			}
		}
	}
}
