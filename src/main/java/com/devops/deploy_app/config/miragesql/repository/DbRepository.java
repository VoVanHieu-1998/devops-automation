package com.devops.deploy_app.config.miragesql.repository;

import jp.xet.sparwings.spring.data.repository.ChunkableRepository;
import jp.xet.sparwings.spring.data.repository.PageableRepository;
import jp.xet.sparwings.spring.data.repository.ScannableRepository;
import jp.xet.sparwings.spring.data.repository.UpsertableRepository;
import jp.xet.sparwings.spring.data.repository.WritableRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface DbRepository<E, ID extends Serializable> extends ScannableRepository<E, ID>, UpsertableRepository<E, ID>,
        WritableRepository<E, ID>, ChunkableRepository<E, ID>, PageableRepository<E, ID> {

}
