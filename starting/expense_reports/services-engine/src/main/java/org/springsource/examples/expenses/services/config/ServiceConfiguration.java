package org.springsource.examples.expenses.services.config;


import org.springframework.beans.factory.annotation.Autowired;
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

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Driver;

@ComponentScan("org.springsource.examples.services")
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

		// localContainerEntityManagerFactoryBean.setPackagesToScan( new String[]{Lesson.class.getPackage().getName()});
		// look ma, no persistence.xml !

		return localContainerEntityManagerFactoryBean;
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
