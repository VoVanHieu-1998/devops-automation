package com.devops.deploy_app.config.miragesql.config;

import com.miragesql.miragesql.SqlExecutor;
import com.miragesql.miragesql.bean.PropertyDesc;
import com.miragesql.miragesql.dialect.Dialect;
import com.miragesql.miragesql.exception.SQLRuntimeException;
import com.miragesql.miragesql.type.LongPrimitiveValueType;
import com.miragesql.miragesql.type.ValueType;
import com.miragesql.miragesql.util.MirageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DbSqlExecutor extends SqlExecutor {

    private Dialect dialect;
    private List<ValueType<?>> valueTypes = new ArrayList<ValueType<?>>();

    @Override
    public void setDialect(Dialect dialect) {
        super.setDialect(dialect);
        this.dialect = dialect;
    }

    @Override
    public void addValueType(ValueType<?> valueType) {
        super.addValueType(valueType);
        this.valueTypes.add(valueType);
    }

    @SuppressWarnings("unchecked")
    protected void setParameters(PreparedStatement stmt, PropertyDesc[] propDescs, Object entity) throws SQLException {
        for (int i = 0; i < propDescs.length; i++) {
            PropertyDesc propertyDesc = propDescs[i];
            if (propertyDesc == null /* || propertyDesc.getValue(entity) == null */) {
                stmt.setObject(i + 1, null);
            } else {
                Class<?> propertType = propertyDesc.getPropertyType();
                @SuppressWarnings("rawtypes")
                ValueType valueType = MirageUtil.getValueType(propertType, propertyDesc, dialect, valueTypes);
                if (valueType != null) {
                    if (propertType == Boolean.TYPE || propertType == Boolean.class) {
                        Long value = 0L;
                        if ((Boolean) propertyDesc.getValue(entity)) {
                            value = 1L;
                        }
                        new LongPrimitiveValueType().set(Long.TYPE, stmt, value, i + 1);
                    } else {
                        valueType.set(propertType, stmt, propertyDesc.getValue(entity), i + 1);
                    }
                } else {
                    if (log.isInfoEnabled()) {
                        log.warn("valueType for " + propertType.getName() + " not found.");
                    }
                }
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void setParameters(PreparedStatement stmt, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] == null) {
                stmt.setObject(i + 1, null);
            } else {
                Class<?> propertType = params[i].getClass();
                ValueType valueType = MirageUtil.getValueType(propertType, null, dialect, valueTypes);
                if (valueType != null) {
                    if (propertType == Boolean.TYPE || propertType == Boolean.class) {
                        Long value = 0L;
                        if (BooleanUtils.isTrue((Boolean) params[i])) {
                            value = 1L;
                        }
                        new LongPrimitiveValueType().set(Long.TYPE, stmt, value, i + 1);
                    } else {
                        valueType.set(propertType, stmt, params[i], i + 1);
                    }
                } else {
                    if (log.isInfoEnabled()) {
                        log.warn("valueType for " + propertType.getName() + " not found.");
                    }
                }
            }
        }
    }

    @Override
    public int executeUpdateSql(String sql, PropertyDesc[] propDescs, Object entity) {
        try {
            return super.executeUpdateSql(sql, propDescs, entity);
        } catch (SQLRuntimeException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
