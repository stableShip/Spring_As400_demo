package com.example.tag.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {


    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sqlite")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }


}
