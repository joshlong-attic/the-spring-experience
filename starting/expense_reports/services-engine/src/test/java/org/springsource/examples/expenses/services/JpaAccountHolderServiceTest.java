package org.springsource.examples.expenses.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springsource.examples.expenses.services.config.ServiceConfiguration;

import javax.inject.Inject;

/***
 *
 * unit test for the {@link JpaAccountHolderService}.
 *
 * @author Josh Long
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServiceConfiguration.class})
public class JpaAccountHolderServiceTest {

	@Inject JpaAccountHolderService accountHolderService ;

	@Test
	public void testAccountHolderCreation() throws Throwable {

	}
}
