package com.devops.deploy_app.repository;

import com.devops.deploy_app.config.miragesql.repository.DbRepository;
import com.devops.deploy_app.entity.Book;
import com.devops.deploy_app.entity.SACTTQTBCTInfo;

import java.util.List;

/**
 * SACTTQTBCTInfoRepository
 *
 * @author hieuvv
 * @version 01-00
 * @since 6/3/2024
 */
public interface BookRepository extends DbRepository<Book, Long> {
    List<Book> findAllData();
}
