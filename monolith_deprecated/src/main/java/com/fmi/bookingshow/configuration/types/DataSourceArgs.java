package com.fmi.bookingshow.configuration.types;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSourceArgs {
    private String databaseUrl;
    private String user;
    private String password;

    public DataSourceArgs(String databaseUrl, String mysqlUser, String mysqlPassword) {
        this.databaseUrl = databaseUrl;
        this.user = mysqlUser;
        this.password = mysqlPassword;
    }
}
