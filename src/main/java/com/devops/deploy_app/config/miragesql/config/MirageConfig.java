package com.devops.deploy_app.config.miragesql.config;

import com.devops.deploy_app.config.miragesql.dialect.DbDialect;
import com.devops.deploy_app.config.miragesql.service.SqlManagerService;
import com.devops.deploy_app.config.miragesql.service.impl.SqlManagerServiceImpl;
import com.devops.deploy_app.config.miragesql.utils.DbUtils;
import com.miragesql.miragesql.bean.BeanDescFactory;
import com.miragesql.miragesql.bean.FieldPropertyExtractor;
import com.miragesql.miragesql.integration.spring.SpringConnectionProvider;
import com.miragesql.miragesql.naming.RailsLikeNameConverter;
import com.miragesql.miragesql.provider.ConnectionProvider;
import jp.xet.springframework.data.mirage.repository.config.EnableMirageRepositories;
import jp.xet.springframework.data.mirage.repository.support.MiragePersistenceExceptionTranslator;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@EnableMirageRepositories(basePackages = "com.devops.deploy_app.repository", sqlManagerRef = "sqlManagerService")
public class MirageConfig {

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private DbType dbType;

    @Bean
    public SqlManagerService sqlManagerService() {

        // bridge java.util.logging used by mirage
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        SqlManagerServiceImpl sqlManagerServiceImpl = null;
        if (null == dbType || null == dbType.getDbDriver()) {
            DatabaseDriver dbDriver = DbUtils.extractDatabaseDriver(transactionManager.getDataSource());
            if (null != dbType) {
                dbType.setDbDriver(dbDriver);
            }
        }
        if (null != dbType) {
            switch (dbType.getDbDriver()) {
            case POSTGRESQL:
                sqlManagerServiceImpl = new SqlManagerServiceImpl(new DbSqlExecutor());
                break;
            default:
                sqlManagerServiceImpl = new SqlManagerServiceImpl();
                break;
            }
        } else {
            sqlManagerServiceImpl = new SqlManagerServiceImpl();
        }

        sqlManagerServiceImpl.setConnectionProvider(connectionProvider());
        sqlManagerServiceImpl.setDialect(new DbDialect(dbType));
        sqlManagerServiceImpl.setBeanDescFactory(beanDescFactory());
        sqlManagerServiceImpl.setNameConverter(new RailsLikeNameConverter());

        return sqlManagerServiceImpl;
    }

    @Bean
    public ConnectionProvider connectionProvider() {
        SpringConnectionProvider springConnectionProvider = new SpringConnectionProvider();
        springConnectionProvider.setTransactionManager(transactionManager);
        return springConnectionProvider;
    }

    @Bean
    public BeanDescFactory beanDescFactory() {
        BeanDescFactory beanDescFactory = new BeanDescFactory();
        beanDescFactory.setPropertyExtractor(new FieldPropertyExtractor());
        return beanDescFactory;
    }

    @Bean
    public MiragePersistenceExceptionTranslator persistenceExceptionTranslator() {
        return new MiragePersistenceExceptionTranslator();
    }

    @Bean
    public DbSLF4JBridgeHandler dbSLF4JBridgeHandler() {
        return new DbSLF4JBridgeHandler();
    }
}
