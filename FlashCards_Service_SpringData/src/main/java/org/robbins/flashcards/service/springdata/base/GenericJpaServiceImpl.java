
package org.robbins.flashcards.service.springdata.base;

import java.io.Serializable;
import java.util.List;

import org.robbins.flashcards.service.base.GenericJpaService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class GenericJpaServiceImpl<T, ID extends Serializable> extends
        GenericPagingAndSortingServiceImpl<T, ID> implements GenericJpaService<T, ID> {

    @Override
    protected abstract JpaRepository<T, ID> getRepository();

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    @Override
    public List<T> save(Iterable<T> entities) {
        return getRepository().save(entities);
    }

    @Override
    public void flush() {
        getRepository().flush();
    }

    @Override
    public T saveAndFlush(T entity) {
        return getRepository().saveAndFlush(entity);
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        getRepository().deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }
}
