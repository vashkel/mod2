package com.epam.esm.repository.config;

import com.epam.esm.config.ProductSpringConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
//@PropertySource("classpath:application.properties")
@EnableJpaRepositories({"com.epam.esm.repository"})
@EntityScan(basePackages = {"com.epam.esm.entity"})
//@SpringBootTest
//@EnableTransactionManagement
//@Import(ProductSpringConfiguration.class)
public class H2Config implements WebMvcConfigurer {

    private static final String PACKAGE_TO_SCAN = "hibernate.scan.package";
    private static final String DDL_AUTO_PROPERTY = "hibernate.hbm2ddl.auto";
    private static final String DIALECT_PROPERTY = "hibernate.dialect";
    private static final String SHOW_SQL_PROPERTY = "hibernate.show_sql";
    private static final String FORMAT_SQL_PROPERTY = "hibernate.format_sql";
    private static final String NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String VALIDATION_MODE_PROPERTY = "javax.persistence.validation.mode";


//    @Autowired
//    private Environment environment;

    @Autowired
    private Environment env;

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//        dataSource.setUrl(env.getProperty("jdbc.url"));
//        dataSource.setUsername(env.getProperty("jdbc.user"));
//        dataSource.setPassword(env.getProperty("jdbc.pass"));
//
//        return dataSource;
//    }
//
//    @Bean
//    @Profile("test")
//    public EntityManager createEntityManager(EntityManagerFactory entityManagerFactory) {
//        return entityManagerFactory.createEntityManager();
//    }
//
//    @Bean
//    @Profile("test")
//    public LocalContainerEntityManagerFactoryBean createLocalContainerEntityManagerFactoryBean(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource);
//        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        entityManagerFactoryBean.setPackagesToScan(PACKAGE_TO_SCAN);
//
//        Properties jpaProperties = new Properties();
//        jpaProperties.put(DIALECT_PROPERTY, Objects.requireNonNull(env.getProperty(DIALECT_PROPERTY)));
//        jpaProperties.put(DDL_AUTO_PROPERTY, Objects.requireNonNull(env.getProperty(DDL_AUTO_PROPERTY)));
//        jpaProperties.put(NAMING_STRATEGY, Objects.requireNonNull(env.getProperty(NAMING_STRATEGY)));
//        jpaProperties.put(SHOW_SQL_PROPERTY, Objects.requireNonNull(env.getProperty(SHOW_SQL_PROPERTY)));
//        jpaProperties.put(FORMAT_SQL_PROPERTY, Objects.requireNonNull(env.getProperty(FORMAT_SQL_PROPERTY)));
//        jpaProperties.put(VALIDATION_MODE_PROPERTY, Objects.requireNonNull(env.getProperty(VALIDATION_MODE_PROPERTY)));
//        entityManagerFactoryBean.setJpaProperties(jpaProperties);
//
//        return entityManagerFactoryBean;
//    }
//
//    @Bean
//    @Profile("test")
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory, DataSource dataSource) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        transactionManager.setDataSource(dataSource);
//        return transactionManager;
//    }

       @Bean
    @Profile("test")
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

//
//    @Bean
//    @Profile("test")
//    @Primary
//    public EntityManager createEntityManager(EntityManagerFactory entityManagerFactory) {
//        return entityManagerFactory.createEntityManager();
//    }
//
//    @Bean
//    @Profile("test")
//    public LocalContainerEntityManagerFactoryBean createLocalContainerEntityManagerFactoryBean(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource);
//        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        entityManagerFactoryBean.setPackagesToScan(PACKAGE_TO_SCAN);
//        Properties jpaProperties = new Properties();
//        jpaProperties.put(DIALECT_PROPERTY, Objects.requireNonNull(environment.getProperty(DIALECT_PROPERTY)));
//        jpaProperties.put(DDL_AUTO_PROPERTY, Objects.requireNonNull(environment.getProperty(DDL_AUTO_PROPERTY)));
//        jpaProperties.put(NAMING_STRATEGY, Objects.requireNonNull(environment.getProperty(NAMING_STRATEGY)));
//        jpaProperties.put(SHOW_SQL_PROPERTY, Objects.requireNonNull(environment.getProperty(SHOW_SQL_PROPERTY)));
//        jpaProperties.put(FORMAT_SQL_PROPERTY, Objects.requireNonNull(environment.getProperty(FORMAT_SQL_PROPERTY)));
//        entityManagerFactoryBean.setJpaProperties(jpaProperties);
//
//        return entityManagerFactoryBean;
//    }
}
