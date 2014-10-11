
package org.robbins.flashcards.service.base;

import java.util.List;

import org.robbins.flashcards.exceptions.DataIntegrityException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class AbstractCrudServiceImpl<T> implements GenericPagingAndSortingService<T, Long>,
        CrudService<T, Long> {

    @Override
    public T save(final T entity) {
        try {
            T result = getRepository().save(entity);
            return result;
        }
        catch (DataIntegrityViolationException integrityException)
        {
            throw new DataIntegrityException("Could save '" + entity.getClass().getSimpleName() + "' due to DataIntegrityViolationException");
        }
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
}
