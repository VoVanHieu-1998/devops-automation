package com.devops.deploy_app.config.miragesql.config;

import com.devops.deploy_app.config.miragesql.utils.DbUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component(value = "dbType")
@Scope(value = "singleton")
@Setter
public class DbType {

    @Getter
    private DatabaseDriver dbDriver;
    private DataSource dataSource;

    public DbType(DataSource dataSource) {
        this.dataSource = dataSource;
        this.dbDriver = DbUtils.extractDatabaseDriver(this.dataSource);
    }
}
