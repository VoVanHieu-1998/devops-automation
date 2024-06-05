package com.devops.deploy_app.config.miragesql.config;

import org.slf4j.bridge.SLF4JBridgeHandler;

public class DbSLF4JBridgeHandler {

    public DbSLF4JBridgeHandler() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
