package com.devops.deploy_app.config.miragesql.service;

import com.devops.deploy_app.config.miragesql.repository.DbRepository;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional(readOnly = true, rollbackFor = Exception.class)
public interface DbRepositoryService<E, ID extends Serializable> {

    public abstract DbRepository<E, ID> initRepo();

    public default long count() {
        return initRepo().count();
    }

    public default Iterable<E> findAll() {
        return initRepo().findAll();
    }

    public default List<E> findAll(Sort sort) {
        return initRepo().findAll(sort);
    }

    public default boolean exists(ID id) {
        return initRepo().exists(id);
    }

    public default E findOne(ID id) {
        return initRepo().findOne(id);
    }

    public default ID getId(E entity) {
        return initRepo().getId(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public default <S extends E> S create(S entity) {
        return initRepo().create(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public default <S extends E> S update(S entity) {
        return initRepo().update(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public default void delete(E entity) {
        initRepo().delete(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public default void delete(ID id) {
        initRepo().delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public default <S extends E> S save(S entity) {
        return initRepo().save(entity);
    }
}
