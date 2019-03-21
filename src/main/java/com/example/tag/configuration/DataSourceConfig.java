package com.example.tag.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "as400DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.as400")
    public DataSource as400DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqliteDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sqlite")
    public DataSource sqliteDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "as400JdbcTemplate")
    public NamedParameterJdbcTemplate primaryJdbcTemplate(@Qualifier("as400DataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
