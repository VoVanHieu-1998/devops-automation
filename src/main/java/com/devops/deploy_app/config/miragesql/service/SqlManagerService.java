package com.devops.deploy_app.config.miragesql.service;

import com.miragesql.miragesql.SqlManager;

import java.util.LinkedHashMap;

public interface SqlManagerService extends SqlManager {

    Long getNextValBySeqName(String sequenceName);

    /**
     * buildInsertMapData
     * <p>
     * mapdata must be a linkedhashmap for put column to position exactly
     * 
     * @param tableName
     * @param mapData
     * @return
     * @author vinhlt
     */
    String buildInsertMapData(String tableName, LinkedHashMap<String, Object> mapData);

    /**
     * excuteInsertMapData
     * <p>
     * mapdata must be a linkedhashmap for put column to position exactly
     * @param tableName
     * @param mapData
     * @author vinhlt
     * @return 
     */
    int excuteInsertMapData(String tableName, LinkedHashMap<String, Object> mapData);
}
