package com.epam.esm.repository.config;

import com.epam.esm.config.ProductSpringConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@TestPropertySource("classpath:test_db.properties")
@SpringBootTest
//@Import(ProductSpringConfiguration.class)
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
//    @Profile("test")
//    public DataSource dataSource(){
//        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
//    }

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
