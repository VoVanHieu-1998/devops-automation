package com.devops.deploy_app.config.miragesql.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Slf4j
public class DbUtils {
    private DbUtils() {
        super();
    }

    public static DatabaseDriver extractDatabaseDriver(DataSource dataSource) {
        DatabaseDriver driver = DatabaseDriver.UNKNOWN;
        try {
            DatabaseMetaData meta = DataSourceUtils.getConnection(dataSource).getMetaData();
            if (null != meta) {
                driver = DatabaseDriver.fromJdbcUrl(meta.getURL());

                if (DatabaseDriver.UNKNOWN.equals(driver)) {
                    driver = DatabaseDriver.fromProductName(JdbcUtils.commonDatabaseName(meta.getDatabaseProductName()));
                }
            }
        } catch (CannotGetJdbcConnectionException | SQLException e) {
            driver = DatabaseDriver.UNKNOWN;
            log.warn("Cannot Extract Database Driver!", e);
        }
        return driver;
    }
}
