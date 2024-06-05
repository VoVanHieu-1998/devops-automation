package com.devops.deploy_app.repository;

import com.devops.deploy_app.config.miragesql.repository.DbRepository;
import com.devops.deploy_app.entity.SACTTQTBCTInfo;
import org.springframework.data.repository.query.Param;

/**
 * SACTTQTBCTInfoRepository
 *
 * @author hieuvv
 * @version 01-00
 * @since 6/3/2024
 */
public interface SACTTQTBCTInfoRepository extends DbRepository<SACTTQTBCTInfo, Long> {
    int countTTQTBCTChecking();
}
