
package org.robbins.flashcards.conversion;

import org.robbins.flashcards.exceptions.ServiceException;

import java.util.List;
import java.util.Set;

public interface DtoConverter<D, E> {

    D getDto(E entity) throws ServiceException;

    D getDto(E entity, Set<String> fields) throws ServiceException;

    E getEntity(D dto);

    List<D> getDtos(List<E> entities, Set<String> fields) throws ServiceException;

    List<E> getEtnties(List<D> entities);
}
