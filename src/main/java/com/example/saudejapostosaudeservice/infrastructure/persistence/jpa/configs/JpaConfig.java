package com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.configs;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.saudejapostosaudeservice.infrastructure.persistence.jpa")
@Profile("jpa")
public class JpaConfig {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Value("${spring.jpa.show-sql}")
    private String showSql;

    @Value("${spring.jpa.properties.hibernate.format_sql}")
    private String formatSql;

    @Bean
    DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(dbUrl)
                .username(dbUsername)
                .password(dbPassword)
                .build();
    }

    @Bean
    JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("com/example/saudejapostosaudeservice/infrastructure/persistence/jpa/models");
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactory.setJpaProperties(additionalProperties());
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.format_sql", formatSql);
        return properties;
    }
}
