
package org.robbins.flashcards.service.base;

import java.io.Serializable;

public interface GenericCrudService<T, ID extends Serializable> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save
     * operation might have changed the entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Saves all given entities.
     *
     * @param entities
     * @return the saved entities
     */
    Iterable<T> save(Iterable<T> entities);

    /**
     * Retrives an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     */
    T findOne(ID id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, alse otherwise
     */
    boolean exists(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<T> findAll();

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @return
     */
    Iterable<T> findAll(Iterable<ID> ids);

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

    /**
     * Deletes a given entity.
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * Deletes the given entities.
     *
     * @param entities
     */
    void delete(Iterable<? extends T> entities);

    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();
}
