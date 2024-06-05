package com.devops.deploy_app.config.miragesql.manager;

import com.devops.deploy_app.config.miragesql.config.DbType;
import lombok.Setter;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Setter

public class DbTransactionManager extends DataSourceTransactionManager {

    /**
     *
     */
    private static final long serialVersionUID = -2056553471320585489L;

    private DbType dbType;

    public DbTransactionManager(DataSource dataSource, DbType dbType) {
        super(dataSource);
        this.dbType = dbType;
        DatabaseDriver dbDriver = this.dbType.getDbDriver();
        switch (dbDriver) {
        case ORACLE:
        case MYSQL:
        case POSTGRESQL:
            super.setEnforceReadOnly(true);
            break;
        default:
            break;
        }
    }
}
