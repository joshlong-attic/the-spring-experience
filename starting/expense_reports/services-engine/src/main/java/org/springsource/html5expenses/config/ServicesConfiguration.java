package org.springsource.html5expenses.config;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springsource.html5expenses.charges.Charge;
import org.springsource.html5expenses.charges.ChargeService;
import org.springsource.html5expenses.charges.implementation.DatabaseChargeService;
import org.springsource.html5expenses.files.ManagedFileService;
import org.springsource.html5expenses.files.implementation.DatabaseManagedFileService;
import org.springsource.html5expenses.reports.ExpenseReportingService;
import org.springsource.html5expenses.reports.implementation.DatabaseExpenseReportingService;
import org.springsource.html5expenses.reports.implementation.Expense;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource("classpath:/services.properties")
@EnableTransactionManagement
public class ServicesConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {

		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);

		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		localContainerEntityManagerFactoryBean.setDataSource(dataSource());
		List <String> pkgs = Arrays.asList(Charge.class.getPackage().getName(), Expense.class.getPackage().getName());
		localContainerEntityManagerFactoryBean.setPackagesToScan( pkgs.toArray(new String [pkgs.size()]) );

		// look ma, no persistence.xml !
		return localContainerEntityManagerFactoryBean;
	}

	@Bean
	public ChargeService chargeService() {
		return new DatabaseChargeService();
	}

	@Bean
	public ManagedFileService managedFileService() {
		return new DatabaseManagedFileService();
	}

	@Bean
	public ExpenseReportingService expenseReportingService() {
		return new DatabaseExpenseReportingService();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	@SuppressWarnings("unchecked")
	public DataSource dataSource() throws Exception {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setPassword(environment.getProperty("dataSource.password"));
		dataSource.setUrl(environment.getProperty("dataSource.url"));
		dataSource.setUsername(environment.getProperty("dataSource.user"));
		dataSource.setDriverClass((Class<Driver>) Class.forName(environment.getProperty("dataSource.driverClass")));
		return dataSource;
	}

}
