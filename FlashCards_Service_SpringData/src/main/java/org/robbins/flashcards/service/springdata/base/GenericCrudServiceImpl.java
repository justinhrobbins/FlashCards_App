
package org.robbins.flashcards.service.springdata.base;

import java.io.Serializable;

import org.robbins.flashcards.service.base.GenericCrudService;
import org.springframework.data.repository.CrudRepository;

public abstract class GenericCrudServiceImpl<T, ID extends Serializable> implements
        GenericCrudService<T, ID> {

    protected abstract CrudRepository<T, ID> getRepository();

    @Override
    public T save(final T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T findOne(final ID id) {
        return getRepository().findOne(id);
    }

    @Override
    public boolean exists(final ID id) {
        return getRepository().exists(id);
    }

    @Override
    public Iterable<T> findAll(final Iterable<ID> ids) {
        return getRepository().findAll(ids);
    }

    @Override
    public Long count() {
        return Long.valueOf(getRepository().count());
    }

    @Override
    public void delete(final ID id) {
        getRepository().delete(id);
    }

    @Override
    public void delete(final T entity) {
        getRepository().delete(entity);
    }

    @Override
    public void delete(final Iterable<? extends T> entities) {
        getRepository().delete(entities);
    }

    @Override
    public void deleteAll() {
        getRepository().deleteAll();
    }
}
