/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springsource.html5expenses.config;

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
import org.springsource.html5expenses.charges.ChargeService;
import org.springsource.html5expenses.charges.implementation.Charge;
import org.springsource.html5expenses.charges.implementation.DatabaseChargeService;
import org.springsource.html5expenses.files.ManagedFileService;
import org.springsource.html5expenses.files.implementation.DatabaseManagedFileService;
import org.springsource.html5expenses.files.implementation.ManagedFile;
import org.springsource.html5expenses.reports.ExpenseReportingService;
import org.springsource.html5expenses.reports.implementation.DatabaseExpenseReportingService;
import org.springsource.html5expenses.reports.implementation.Expense;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		List<String> pkgs = Arrays.asList(Charge.class.getPackage().getName(), ManagedFile.class.getPackage().getName(), Expense.class.getPackage().getName());


		Map<String, String> mapOfJpaProperties = new HashMap<String, String>();
		mapOfJpaProperties.put("hibernate.hbm2ddl.auto", "create");

		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		localContainerEntityManagerFactoryBean.setJpaPropertyMap(mapOfJpaProperties);
		localContainerEntityManagerFactoryBean.setDataSource(dataSource());
		localContainerEntityManagerFactoryBean.setPackagesToScan(pkgs.toArray(new String[pkgs.size()]));

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
