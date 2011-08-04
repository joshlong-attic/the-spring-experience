package org.springsource.examples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springsource.examples.domain.Lesson;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@ComponentScan("org.springsource.examples.services")
@Configuration
@EnableTransactionManagement
public class ServicesConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan( new String[]{Lesson.class.getPackage().getName()});

        // look ma, no persistence.xml !
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public DataSource dataSource() throws Exception {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
     }

}
