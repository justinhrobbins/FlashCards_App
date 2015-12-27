
package org.robbins.flashcards.service.base;

import java.io.Serializable;
import java.util.List;

import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.FlashcardsException;

public interface GenericCrudService<T, ID extends Serializable> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save
     * operation might have changed the entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    T save(T entity) throws FlashcardsException;

    BatchLoadingReceiptDto save(List<T> entities) throws FlashcardsException;

    /**
     * Retrives an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     */
    T findOne(ID id) throws FlashcardsException;

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    Long count();

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     */
    void delete(ID id);

	List<T> findAll() throws FlashcardsException;
}
