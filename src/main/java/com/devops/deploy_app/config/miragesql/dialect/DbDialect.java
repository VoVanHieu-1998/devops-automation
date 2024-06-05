package com.devops.deploy_app.config.miragesql.dialect;

import com.devops.deploy_app.config.miragesql.config.DbType;
import com.miragesql.miragesql.annotation.PrimaryKey.GenerationType;
import com.miragesql.miragesql.dialect.MySQLDialect;
import com.miragesql.miragesql.dialect.OracleDialect;
import com.miragesql.miragesql.dialect.PostgreSQLDialect;
import com.miragesql.miragesql.dialect.StandardDialect;
import com.miragesql.miragesql.type.OracleResultSetValueType;
import com.miragesql.miragesql.type.PostgreResultSetValueType;
import com.miragesql.miragesql.type.ValueType;
import lombok.Setter;
import org.springframework.boot.jdbc.DatabaseDriver;

@Setter
public class DbDialect extends StandardDialect {

    private OracleResultSetValueType valueTypeOracle = new OracleResultSetValueType();
    private PostgreResultSetValueType valueTypePostgre = new PostgreResultSetValueType();

    private DbType dbType;

    public DbDialect(DbType dbType) {
        this.dbType = dbType;
    }

    @Override
    public String getName() {
        if (null != dbType) {
            DatabaseDriver driver = dbType.getDbDriver();
            switch (driver) {
            case MYSQL:
                return new MySQLDialect().getName();
            case ORACLE:
                return new OracleDialect().getName();
            case SQLSERVER:
                return new SQLServerDialect().getName();
            case POSTGRESQL:
                return new PostgreSQLDialect().getName();
            default:
                break;
            }
        }
        return null;
    }

    @Override
    public ValueType<?> getValueType() {
        if (null != dbType) {
            DatabaseDriver driver = dbType.getDbDriver();
            switch (driver) {
            case ORACLE:
                return valueTypeOracle;
            case POSTGRESQL:
                return valueTypePostgre;
            default:
                break;
            }
        }
        return super.getValueType();
    }

    @Override
    public String getSequenceSql(String sequenceName) {
        if (null != dbType) {
            DatabaseDriver driver = dbType.getDbDriver();
            switch (driver) {
            case ORACLE:
                return new OracleDialect().getSequenceSql(sequenceName);
            case SQLSERVER:
                return new SQLServerDialect().getSequenceSql(sequenceName);
            case POSTGRESQL:
                return new PostgreSQLDialect().getSequenceSql(sequenceName);
            default:
                break;
            }
        }
        return super.getSequenceSql(sequenceName);
    }

    @Override
    public boolean supportsGenerationType(GenerationType generationType) {
        if (null != dbType) {
            DatabaseDriver driver = dbType.getDbDriver();
            switch (driver) {
            case ORACLE:
                return new OracleDialect().supportsGenerationType(generationType);
            case SQLSERVER:
                return new SQLServerDialect().supportsGenerationType(generationType);
            case POSTGRESQL:
                return new PostgreSQLDialect().supportsGenerationType(generationType);
            default:
                break;
            }
        }
        return super.supportsGenerationType(generationType);
    }

    @Override
    public boolean needsParameterForResultSet() {
        if (null != dbType) {
            DatabaseDriver driver = dbType.getDbDriver();
            switch (driver) {
            case ORACLE:
                return true;
            default:
                break;
            }
        }
        return super.needsParameterForResultSet();
    }

    @Override
    public String getCountSql(String sql) {
        if (null != dbType) {
            DatabaseDriver driver = dbType.getDbDriver();
            switch (driver) {
            case MYSQL:
                return new MySQLDialect().getCountSql(sql);
            default:
                break;
            }
        }
        return super.getCountSql(sql);
    }
}
