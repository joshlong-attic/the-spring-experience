package org.springsource.examples.expenses.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springsource.examples.expenses.charges.Charge;
import org.springsource.examples.expenses.charges.ChargeBatch;
import org.springsource.examples.expenses.fs.ManagedFile;
import org.springsource.examples.expenses.fs.StorageNode;
import org.springsource.examples.expenses.reports.*;
import org.springsource.examples.expenses.users.CreditCard;
import org.springsource.examples.expenses.users.ExpenseHolder;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Driver;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


// todo package this whole thing into a war
// todo would be more natural to root all this stuff under 'services' or something as opposed to the web configuration-related stuff. then i could simply specify one package below
@ComponentScan(basePackages = {
  "org.springsource.examples.expenses.reports",
  "org.springsource.examples.expenses.charges",
  "org.springsource.examples.expenses.users",
  "org.springsource.examples.expenses.fs"})
@Configuration
@PropertySource("classpath:/services.properties")
@EnableTransactionManagement
public class ServiceConfiguration {


	@Inject private Environment environment;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {

		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);

		Map<String, String> props = new HashMap<String, String>();

		// validate or create
		props.put("hibernate.hbm2ddl.auto", "create");

		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		localContainerEntityManagerFactoryBean.setDataSource(dataSource());
		localContainerEntityManagerFactoryBean.setJpaPropertyMap(props);


		Class<?>[] entityClasses = { Charge.class, ChargeBatch.class, ManagedFile.class, StorageNode.class, Attachment.class, ExpenseReport.class, ExpenseReportAuthorization.class, ExpenseReportLine.class, ExpenseReportService.class, CreditCard.class, ExpenseHolder.class};
		Set<String> packages = new HashSet<String>();

		for (Class<?> clzz : entityClasses) {
			packages.add(clzz.getPackage().getName());
		}

		localContainerEntityManagerFactoryBean.setPackagesToScan(packages.toArray(new String[packages.size()]));

		// look ma, no persistence.xml !
		return localContainerEntityManagerFactoryBean;
	}


	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		EntityManagerFactory entityManagerFactory = this.entityManagerFactory().getObject();
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	@SuppressWarnings("unchecked")
	public DataSource dataSource() throws Exception {

		Class<Driver> driverClass = (Class<Driver>) Class.forName(environment.getProperty("dataSource.driverClassName"));

		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setPassword(environment.getProperty("dataSource.password"));
		dataSource.setUrl(environment.getProperty("dataSource.url"));
		dataSource.setUsername(environment.getProperty("dataSource.user"));
		dataSource.setDriverClass(driverClass);
		return dataSource;
	}

}
