
package org.robbins.flashcards.service.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Sort;

public interface GenericJpaService<T, ID extends Serializable> extends
        GenericPagingAndSortingService<T, ID> {

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.CrudRepository#findAll()
     */
    @Override
    List<T> findAll();

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.
     * springframework.data.domain.Sort)
     */
    @Override
    List<T> findAll(Sort sort);

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
     */
    @Override
    List<T> save(Iterable<T> entities);

    /**
     * Flushes all pending changes to the database.
     */
    void flush();

    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity
     * @return the saved entity
     */
    T saveAndFlush(T entity);

    /**
     * Deletes the given entities in a batch which means it will create a single Query.
     * Assume that we will clear the {@link javax.persistence.EntityManager} after the
     * call.
     *
     * @param entities
     */
    void deleteInBatch(Iterable<T> entities);

    /**
     * Deletes all entites in a batch call.
     */
    void deleteAllInBatch();
}
