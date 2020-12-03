package com.epam.esm.repository.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySource("classpath:test_db.properties")
@ComponentScan(basePackages = "com.epam.esm")
@EnableWebMvc
public class H2Config implements WebMvcConfigurer {

    private static final String PACKAGE_TO_SCAN = "com.epam.esm";
    private static final String DDL_AUTO_PROPERTY = "hibernate.hbm2ddl.auto";
    private static final String DIALECT_PROPERTY = "hibernate.dialect";
    private static final String SHOW_SQL_PROPERTY = "hibernate.show_sql";
    private static final String FORMAT_SQL_PROPERTY = "hibernate.format_sql";
    private static final String NAMING_STRATEGY = "hibernate.ejb.naming_strategy";

    @Autowired
    private Environment environment;

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource ();
//        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
//        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
//        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
//        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
//        return dataSource;
//    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("db.driver"));
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.user"));
        dataSource.setPassword(environment.getProperty("db.password"));
        dataSource.setInitialSize(Integer.parseInt(environment.getProperty("db.pool")));
        return dataSource;
    }

    @Bean
    @Primary
    public EntityManager createEntityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean createLocalContainerEntityManagerFactoryBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(PACKAGE_TO_SCAN);

        Properties jpaProperties = new Properties();
        jpaProperties.put(DIALECT_PROPERTY, Objects.requireNonNull(environment.getProperty(DIALECT_PROPERTY)));
        jpaProperties.put(DDL_AUTO_PROPERTY, Objects.requireNonNull(environment.getProperty(DDL_AUTO_PROPERTY)));
        jpaProperties.put(NAMING_STRATEGY, Objects.requireNonNull(environment.getProperty(NAMING_STRATEGY)));
        jpaProperties.put(SHOW_SQL_PROPERTY, Objects.requireNonNull(environment.getProperty(SHOW_SQL_PROPERTY)));
        jpaProperties.put(FORMAT_SQL_PROPERTY, Objects.requireNonNull(environment.getProperty(FORMAT_SQL_PROPERTY)));
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate();
//        jdbcTemplate.setDataSource(dataSource);
//        return new JdbcTemplate(dataSource);
//    }
}
