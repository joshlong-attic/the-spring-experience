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
import org.springsource.examples.expenses.model.Charge;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Driver;

@ComponentScan("org.springsource.examples.expenses.services")
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

		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		localContainerEntityManagerFactoryBean.setDataSource(dataSource());

		String entityPackage = Charge.class.getPackage().getName();
		localContainerEntityManagerFactoryBean.setPackagesToScan(new String[]{entityPackage});

		// look ma, no persistence.xml !
		return localContainerEntityManagerFactoryBean;
	}


	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		EntityManagerFactory entityManagerFactory =this.entityManagerFactory().getObject();
		return new JpaTransactionManager(entityManagerFactory) ;
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
