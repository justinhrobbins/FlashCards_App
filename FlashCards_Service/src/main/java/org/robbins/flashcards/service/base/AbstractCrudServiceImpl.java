
package org.robbins.flashcards.service.base;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class AbstractCrudServiceImpl<T> implements GenericJpaService<T, Long>,
        CrudService<T, Long> {

    @Override
    public T save(final T entity) {
        return getRepository().save(entity);
    }

    @Override
    public Long count() {
        return getRepository().count();
    }

    @Override
    public T findOne(final Long id) {
        return getRepository().findOne(id);
    }

    @Override
    public void delete(final Long id) {
        getRepository().delete(id);
    }

    @Override
    public void delete(final T entity) {
        getRepository().delete(entity);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public List<T> findAll(final Sort sort) {
        return getRepository().findAll(sort);
    }

    @Override
    public List<T> findAll(final Pageable pageable) {
        return getRepository().findAll(pageable).getContent();
    }

    @Override
    public List<T> save(final Iterable<T> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub
    }

    @Override
    public T saveAndFlush(final T entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteInBatch(final Iterable<T> entities) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean exists(final Long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterable<T> findAll(final Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(final Iterable<? extends T> entities) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
    }
}
