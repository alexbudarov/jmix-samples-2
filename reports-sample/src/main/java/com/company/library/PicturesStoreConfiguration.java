package com.company.library;

import io.jmix.autoconfigure.data.JmixLiquibaseCreator;
import io.jmix.core.JmixModules;
import io.jmix.core.Resources;
import io.jmix.data.impl.JmixEntityManagerFactoryBean;
import io.jmix.data.impl.JmixTransactionManager;
import io.jmix.data.persistence.DbmsSpecifics;
import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class PicturesStoreConfiguration {

    @Bean
    @ConfigurationProperties("pictures.datasource")
    DataSourceProperties picturesDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "pictures.datasource.hikari")
    DataSource picturesDataSource(@Qualifier("picturesDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean picturesEntityManagerFactory(
            @Qualifier("picturesDataSource") DataSource dataSource,
            JpaVendorAdapter jpaVendorAdapter,
            DbmsSpecifics dbmsSpecifics,
            JmixModules jmixModules,
            Resources resources
    ) {
        return new JmixEntityManagerFactoryBean("pictures", dataSource, jpaVendorAdapter, dbmsSpecifics, jmixModules, resources);
    }

    @Bean
    JpaTransactionManager picturesTransactionManager(@Qualifier("picturesEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JmixTransactionManager("pictures", entityManagerFactory);
    }

    @Bean("picturesLiquibaseProperties")
    @ConfigurationProperties(prefix = "pictures.liquibase")
    public LiquibaseProperties picturesLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean("picturesLiquibase")
    public SpringLiquibase picturesLiquibase(@Qualifier("picturesDataSource") DataSource dataSource,
                                             @Qualifier("picturesLiquibaseProperties") LiquibaseProperties liquibaseProperties) {
        return JmixLiquibaseCreator.create(dataSource, liquibaseProperties);
    }
}
