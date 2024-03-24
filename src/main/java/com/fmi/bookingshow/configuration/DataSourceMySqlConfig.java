package com.fmi.bookingshow.configuration;

import com.fmi.bookingshow.configuration.types.DataSourceArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("MySQL")
public class DataSourceMySqlConfig extends AbstractDataSourceConfig {
    Logger logger = LoggerFactory.getLogger(DataSourceMySqlConfig.class);

    private DataSourceArgs dataSourceArgs;

    public void initializeArgsForConnection() {
        if (System.getenv("DATABASE_URL") == null) {
            logger.info("Using default database (dev environment)");
            this.dataSourceArgs = new DataSourceArgs(
                    "jdbc:mysql://localhost:3306/devdb",
                    "alex",
                    "password"
            );
        } else {
            logger.info("Using database credentials from env (prod environment)");
            this.dataSourceArgs = new DataSourceArgs(
                    System.getenv("DATABASE_URL"),
                    System.getenv("MYSQL_USER"),
                    System.getenv("MYSQL_PASSWORD")
            );
        }
    }

    @Bean
    public DataSource dataSource() {
        this.initializeArgsForConnection();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        logger.info("Connection to database using followng url: " + dataSourceArgs.getDatabaseUrl());
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(dataSourceArgs.getDatabaseUrl());
        dataSource.setUsername(dataSourceArgs.getUser());
        dataSource.setPassword(dataSourceArgs.getPassword());
        return dataSource;
    }
}
