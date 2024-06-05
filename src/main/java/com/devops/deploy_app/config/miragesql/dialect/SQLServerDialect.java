package com.devops.deploy_app.config.miragesql.dialect;

import com.miragesql.miragesql.annotation.PrimaryKey.GenerationType;
import com.miragesql.miragesql.dialect.StandardDialect;

public class SQLServerDialect extends StandardDialect {

    @Override
    public String getName() {
        return "sqlserver";
    }

    @Override
    public String getSequenceSql(String sequenceName) {
        return String.format("SELECT (NEXT VALUE FOR %s) AS NEXTVAL", sequenceName);
    }

    @Override
    public boolean supportsGenerationType(GenerationType generationType) {
        if (generationType == GenerationType.IDENTITY) {
            return false;
        }
        return true;
    }
}
