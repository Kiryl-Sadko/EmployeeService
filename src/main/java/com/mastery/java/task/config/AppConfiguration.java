package com.mastery.java.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:datasource.properties")
@ComponentScan(basePackages = "com.mastery.java.task")
public class AppConfiguration {

    private static final String DB_DRIVER = "datasource.driver";
    private static final String DB_URL = "datasource.url";
    private static final String DB_USER = "datasource.user";
    private static final String DB_PASSWORD = "datasource.password";

    private final Environment environment;

    public AppConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty(DB_DRIVER));
        dataSource.setUrl(environment.getRequiredProperty(DB_URL));
        dataSource.setUsername(environment.getRequiredProperty(DB_USER));
        dataSource.setPassword(environment.getRequiredProperty(DB_PASSWORD));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
