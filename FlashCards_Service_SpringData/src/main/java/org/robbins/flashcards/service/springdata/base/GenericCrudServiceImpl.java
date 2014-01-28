
package org.robbins.flashcards.service.springdata.base;

import java.io.Serializable;

import org.robbins.flashcards.service.base.GenericCrudService;
import org.springframework.data.repository.CrudRepository;

public abstract class GenericCrudServiceImpl<T, ID extends Serializable> implements
        GenericCrudService<T, ID> {

    protected abstract CrudRepository<T, ID> getRepository();

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T findOne(ID id) {
        return getRepository().findOne(id);
    }

    @Override
    public boolean exists(ID id) {
        return getRepository().exists(id);
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return getRepository().findAll(ids);
    }

    @Override
    public Long count() {
        return Long.valueOf(getRepository().count());
    }

    @Override
    public void delete(ID id) {
        getRepository().delete(id);
    }

    @Override
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        getRepository().delete(entities);
    }

    @Override
    public void deleteAll() {
        getRepository().deleteAll();
    }
}
