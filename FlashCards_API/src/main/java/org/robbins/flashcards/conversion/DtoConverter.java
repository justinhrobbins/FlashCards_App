
package org.robbins.flashcards.conversion;

import java.util.List;
import java.util.Set;

import org.robbins.flashcards.exceptions.RepositoryException;

public interface DtoConverter<D, E> {

    D getDto(E entity) throws RepositoryException;
    D getDto(E entity, Set<String> fields) throws RepositoryException;
    E getEntity(D dto);
    List<D> getDtos(List<E> entities) throws RepositoryException;
    List<D> getDtos(List<E> entities, Set<String> fields) throws RepositoryException;
    List<E> getEtnties(List<D> entities);
}
