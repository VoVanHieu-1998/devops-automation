package com.devops.deploy_app.config.miragesql.service.impl;

import com.devops.deploy_app.config.miragesql.repository.DbRepository;
import com.devops.deploy_app.config.miragesql.service.SqlManagerService;
import com.miragesql.miragesql.DefaultEntityOperator;
import com.miragesql.miragesql.SqlExecutor;
import com.miragesql.miragesql.SqlManagerImpl;
import com.miragesql.miragesql.SqlResource;
import com.miragesql.miragesql.bean.BeanDesc;
import com.miragesql.miragesql.bean.BeanDescFactory;
import com.miragesql.miragesql.bean.PropertyDesc;
import com.miragesql.miragesql.exception.SQLRuntimeException;
import com.miragesql.miragesql.naming.DefaultNameConverter;
import com.miragesql.miragesql.parser.Node;
import com.miragesql.miragesql.parser.SqlContext;
import com.miragesql.miragesql.type.BigDecimalValueType;
import com.miragesql.miragesql.type.BooleanPrimitiveValueType;
import com.miragesql.miragesql.type.BooleanValueType;
import com.miragesql.miragesql.type.ByteArrayValueType;
import com.miragesql.miragesql.type.DoublePrimitiveValueType;
import com.miragesql.miragesql.type.DoubleValueType;
import com.miragesql.miragesql.type.FloatPrimitiveValueType;
import com.miragesql.miragesql.type.FloatValueType;
import com.miragesql.miragesql.type.IntegerPrimitiveValueType;
import com.miragesql.miragesql.type.IntegerValueType;
import com.miragesql.miragesql.type.LongPrimitiveValueType;
import com.miragesql.miragesql.type.LongValueType;
import com.miragesql.miragesql.type.ShortPrimitiveValueType;
import com.miragesql.miragesql.type.ShortValueType;
import com.miragesql.miragesql.type.SqlDateValueType;
import com.miragesql.miragesql.type.StringValueType;
import com.miragesql.miragesql.type.TimeValueType;
import com.miragesql.miragesql.type.TimestampValueType;
import com.miragesql.miragesql.type.UtilDateValueType;
import com.miragesql.miragesql.type.enumerate.EnumOneBasedOrdinalValueType;
import com.miragesql.miragesql.type.enumerate.EnumOrdinalValueType;
import com.miragesql.miragesql.type.enumerate.EnumStringValueType;
import com.miragesql.miragesql.util.JdbcUtil;
import jp.xet.springframework.data.mirage.repository.ScopeClasspathSqlResource;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("sqlManagerService")
public class SqlManagerServiceImpl extends SqlManagerImpl implements SqlManagerService {

    private static final Logger logger = LoggerFactory.getLogger(SqlExecutor.class);
    private static final String sqlResourceErrorMessage = "SqlResource error. {}";

    public SqlManagerServiceImpl(SqlExecutor sqlExecutor) {
        super.sqlExecutor = sqlExecutor;
        super.addValueType(new StringValueType());
        super.addValueType(new IntegerValueType());
        super.addValueType(new IntegerPrimitiveValueType());
        super.addValueType(new LongValueType());
        super.addValueType(new LongPrimitiveValueType());
        super.addValueType(new ShortValueType());
        super.addValueType(new ShortPrimitiveValueType());
        super.addValueType(new DoubleValueType());
        super.addValueType(new DoublePrimitiveValueType());
        super.addValueType(new FloatValueType());
        super.addValueType(new FloatPrimitiveValueType());
        super.addValueType(new BooleanValueType());
        super.addValueType(new BooleanPrimitiveValueType());
        super.addValueType(new BigDecimalValueType());
        super.addValueType(new SqlDateValueType());
        super.addValueType(new UtilDateValueType());
        super.addValueType(new TimeValueType());
        super.addValueType(new TimestampValueType());
        super.addValueType(new ByteArrayValueType());
        super.addValueType(new EnumStringValueType());
        super.addValueType(new EnumOrdinalValueType());
        super.addValueType(new EnumOneBasedOrdinalValueType());
        // addValueType(new com.miragesql.miragesql.type.DefaultValueType());

        super.setDialect(dialect);
        super.setBeanDescFactory(new BeanDescFactory());
        super.setNameConverter(new DefaultNameConverter());
        super.setEntityOperator(new DefaultEntityOperator());

        //
    }

    public SqlManagerServiceImpl() {
        super();
    }

    protected Node prepareNode(SqlResource resource) {
        // Note: Check super.prepareNode when change version mirage
        if (logger.isInfoEnabled()) {
            logger.info(resource.toString());
        }
        return super.prepareNode(resource);
    }

    public int executeUpdate(SqlResource resource, Object param) {
        String sql = null;
        Object[] params = null;
        try {
            // Note: Check super.executeUpdate when change version mirage
            Node node = prepareNode(resource);
            SqlContext context = prepareSqlContext(param);
            node.accept(context);

            sql = context.getSql();
            params = context.getBindVariables();
            return sqlExecutor.executeUpdateSql(sql, params, null);
        } catch (Exception e) {
            logger.error(sqlResourceErrorMessage, resource.toString());
            printSqlWhenError(sql);
            printParametersWhenError(params);
            throw e;
        }
    }

    public <T> List<T> getResultList(Class<T> clazz, SqlResource resource, Object param) {
        String sql = null;
        Object[] params = null;
        try {
            // Note: Check super.getResultList when change version mirage
            Node node = prepareNode(resource);
            SqlContext context = prepareSqlContext(param);
            node.accept(context);

            sql = context.getSql();
            params = context.getBindVariables();
            return super.sqlExecutor.getResultList(clazz, sql, params);
        } catch (Exception e) {
            logger.error(sqlResourceErrorMessage, resource.toString());
            printSqlWhenError(sql);
            printParametersWhenError(params);
            throw e;
        }
    }

    public <T> T getSingleResult(Class<T> clazz, SqlResource resource, Object param) {
        String sql = null;
        Object[] params = null;
        try {
            // Note: Check super.getSingleResult when change version mirage
            Node node = prepareNode(resource);
            SqlContext context = super.prepareSqlContext(param);
            node.accept(context);

            sql = context.getSql();
            params = context.getBindVariables();
            return super.sqlExecutor.getSingleResult(clazz, sql, params);
        } catch (Exception e) {
            logger.error(sqlResourceErrorMessage, resource.toString());
            printSqlWhenError(sql);
            printParametersWhenError(params);
            throw e;
        }
    }

    public Long getNextValBySeqName(String seqName) {
        String querySql = dialect.getSequenceSql(seqName);
        Long sequenceValue = super.sqlExecutor.getSingleResult(Long.class, querySql, new Object[0]);
        return sequenceValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.db.service.SqlManagerService#buildInsertMapData(java.lang.String, java.util.LinkedHashMap)
     */
    @Override
    public String buildInsertMapData(String tableName, LinkedHashMap<String, Object> mapData) {
        ScopeClasspathSqlResource resource = new ScopeClasspathSqlResource(DbRepository.class, "BaseInsert.sql");
        Node node = prepareNode(resource);
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("tableName", tableName);
        SqlContext context = prepareSqlContext(mapParam);
        node.accept(context);

        StringBuilder sbCol = new StringBuilder(context.getSql());
        StringBuilder sbValue = new StringBuilder(" VALUES(");
        for (Map.Entry<String, Object> entry : mapData.entrySet()) {
            sbCol.append("[").append(entry.getKey()).append("],");
            sbValue.append("?,");
        }
        String cols = sbCol.toString();
        cols = cols.substring(0, cols.length() - 1) + ") ";
        String values = sbValue.toString();
        values = values.substring(0, values.length() - 1) + ") ";
        return cols + values;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.db.service.SqlManagerService#excuteInsertMapData(java.lang.String, java.util.LinkedHashMap)
     */
    @Override
    public int excuteInsertMapData(String tableName, LinkedHashMap<String, Object> mapData) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Object[] params = mapData.values().toArray(new Object[mapData.values().size()]);
            String sql = buildInsertMapData(tableName, mapData);
            stmt = connectionProvider.getConnection().prepareStatement(sql);
            setParameters(stmt, params);

            if (logger.isInfoEnabled()) {
                printSql(sql);
                printParameters(params);
            }
            int result = stmt.executeUpdate();
            return result;
        } catch (SQLException ex) {
            throw new SQLRuntimeException(ex);

        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(stmt);
        }
    }

    /**
     * setParameters
     * 
     * @param stmt
     * @param params
     * @author vinhlt
     * @throws SQLException
     */
    private void setParameters(PreparedStatement stmt, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            if (param instanceof Character) {
                param = "" + param;
            }
            if (param instanceof Date) {
                param = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(param);
            }

            stmt.setObject(i + 1, param);
        }
    }

    private static void printSql(String sql) {
        sql = sql.replace("\r\n", "\n");
        sql = sql.replace("\r", "\n");

        StringBuilder sb = new StringBuilder();
        for (String line : sql.split("\n")) {
            if (line.trim().length() != 0) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
        }

        logger.info(sb.toString().trim());
    }

    private static void printParameters(Object[] params) {
        if (params == null) {
            return;
        }
        for (int i = 0; i < params.length; i++) {
            logger.info(String.format("params[%d]=%s", i, params[i]));
        }
    }

    private void printSqlWhenError(String sql) {
        if (sql == null) {
            return;
        }
        logger.error(sql);
    }

    private void printParametersWhenError(Object[] params) {
        if (params == null) {
            return;
        }
        for (int i = 0; i < params.length; ++i) {
            logger.error(String.format("params[%d]=%s", i, params[i]));
        }
    }
    
    protected String toCallString(String moduleName, Object param, boolean function) {

        if (!"postgresql".equals(dialect.getName())) {
            return super.toCallString(moduleName, param, function);
        }

        // Remove "{" "}" for postgresql
        StringBuilder sb = new StringBuilder();

        if (function) {
            // sb.append("{? = call ");
            sb.append("? = call ");
        } else {
            // sb.append("{call ");
            sb.append("call ");
        }

        sb.append(moduleName);
        sb.append("(");
        if (param != null) {
            StringBuilder p = new StringBuilder();
            BeanDesc beanDesc = beanDescFactory.getBeanDesc(param);
            int parameterCount = 0;
            for (int i = 0; i < beanDesc.getPropertyDescSize(); i++) {
                PropertyDesc pd = beanDesc.getPropertyDesc(i);
                if (needsParameter(pd)) {
                    if (parameterCount > 0) {
                        p.append(", ");
                    }
                    if (parameterCount >= 0) {
                        p.append("?");
                    }
                    parameterCount++;
                }
            }
            sb.append(p.toString());
        }
        sb.append(")");
        // sb.append("}");
        sb.append("");

        return sb.toString();
    }
}
