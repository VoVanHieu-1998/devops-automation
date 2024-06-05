package com.devops.deploy_app.config.miragesql.config;

import com.devops.deploy_app.config.miragesql.manager.DbTransactionManager;
import com.devops.deploy_app.config.miragesql.utils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class TransactionManagerConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DbType dbType;

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DatabaseDriver dbDriver = DbUtils.extractDatabaseDriver(dataSource);
        dbType.setDbDriver(dbDriver);
        return new DbTransactionManager(dataSource, dbType);
    }
}
