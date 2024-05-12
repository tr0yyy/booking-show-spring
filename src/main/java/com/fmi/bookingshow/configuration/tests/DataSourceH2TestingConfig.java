package com.fmi.bookingshow.configuration.tests;

import com.fmi.bookingshow.configuration.AbstractDataSourceConfig;
import com.fmi.bookingshow.configuration.types.DataSourceArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@Slf4j
@Profile("H2-Testing")
public class DataSourceH2TestingConfig extends AbstractDataSourceConfig {
    public void initializeArgsForConnection() {
        log.info("Using H2 in-memory database for testing");
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
}
