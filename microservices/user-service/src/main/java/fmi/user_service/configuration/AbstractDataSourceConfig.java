package fmi.user_service.configuration;

import javax.sql.DataSource;

abstract public class AbstractDataSourceConfig {
    /**
     * requires private attribute on extended class
     */
    abstract protected void initializeArgsForConnection();
    abstract protected DataSource dataSource();
}
