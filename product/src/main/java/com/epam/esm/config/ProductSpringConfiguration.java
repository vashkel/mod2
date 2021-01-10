package com.epam.esm.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.epam.esm")
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class ProductSpringConfiguration implements WebMvcConfigurer {

    private static final String PACKAGE_TO_SCAN = "com.epam.esm";
    private static final String DDL_AUTO_PROPERTY = "hibernate.hbm2ddl.auto";
    private static final String DIALECT_PROPERTY = "hibernate.dialect";
    private static final String SHOW_SQL_PROPERTY = "hibernate.show_sql";
    private static final String FORMAT_SQL_PROPERTY = "hibernate.format_sql";
    private static final String NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String ENVERS_NAMING_SUFFIX = "org.hibernate.envers.audit_table_suffix";
    private static final String ENVERS_REVISION_FIELD_NAME = "org.hibernate.envers.revision_field_name";
    private static final String ENVERS_REVISION_TYPE_FIELD_NAME = "org.hibernate.envers.revision_type_field_name";
    private static final String ENVERS_AUDIT_STRATEGY = "org.hibernate.envers.audit_strategy";
    private static final String ENVERS_AUDIT_STRATEGY_VALID_AND_REV_NAME =
            "org.hibernate.envers.audit_strategy_validity_end_rev_field_name";
    private static final String ENVERS_AUDIT_STRATEGY_VALID_AND_STORE_TIMESTAMP =
            "org.hibernate.envers.audit_strategy_validity_store_revend_timestamp";
    private static final String ENVERS_AUDIT_STRATEGY_VALID_REVENT_TIMESTAMP_FIELD_NAME =
            "org.hibernate.envers.audit_strategy_validity_revend_timestamp_field_name";
    private static final String ENVERS_AUDIT_STORE_DATA_AT_DELETE =
            "org.hibernate.envers.store_data_at_delete";



    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        dataSource.setDriverClassName(Objects.requireNonNull(environment
                .getProperty("spring.datasource.driver-class-name")));
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
        jpaProperties.put(ENVERS_NAMING_SUFFIX, Objects.requireNonNull(environment.getProperty(ENVERS_NAMING_SUFFIX)));
        jpaProperties.put(ENVERS_REVISION_FIELD_NAME,
                Objects.requireNonNull(environment.getProperty(ENVERS_REVISION_FIELD_NAME)));
        jpaProperties.put(ENVERS_REVISION_TYPE_FIELD_NAME,
                Objects.requireNonNull(environment.getProperty(ENVERS_REVISION_TYPE_FIELD_NAME)));
        jpaProperties.put(ENVERS_AUDIT_STRATEGY,
                Objects.requireNonNull(environment.getProperty(ENVERS_AUDIT_STRATEGY)));
        jpaProperties.put(ENVERS_AUDIT_STRATEGY_VALID_AND_REV_NAME,
                Objects.requireNonNull(environment.getProperty(ENVERS_AUDIT_STRATEGY_VALID_AND_REV_NAME)));
        jpaProperties.put(ENVERS_AUDIT_STRATEGY_VALID_AND_STORE_TIMESTAMP,
                Objects.requireNonNull(environment.getProperty(ENVERS_AUDIT_STRATEGY_VALID_AND_STORE_TIMESTAMP)));
        jpaProperties.put(ENVERS_AUDIT_STRATEGY_VALID_REVENT_TIMESTAMP_FIELD_NAME,
                Objects.requireNonNull(environment.getProperty(ENVERS_AUDIT_STORE_DATA_AT_DELETE)));
        jpaProperties.put(ENVERS_AUDIT_STORE_DATA_AT_DELETE,
                Objects.requireNonNull(environment.getProperty(ENVERS_AUDIT_STORE_DATA_AT_DELETE)));
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory, DataSource dataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:locale/locale");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("en"));
        return slr;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    @Profile("dev")
    public DataSource testDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.url.test"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username.test"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password.test"));
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name.test"));
        dataSource.setInitialSize(Integer.parseInt(Objects.requireNonNull(environment
                .getProperty("spring.datasource.db.pool.test"))));
        return dataSource;
    }

    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer = new
                LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        return Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }
}
