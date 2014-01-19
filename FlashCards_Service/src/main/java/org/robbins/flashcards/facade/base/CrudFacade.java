
package org.robbins.flashcards.facade.base;

import java.util.List;
import java.util.Set;

import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.service.base.GenericJpaService;

public interface CrudFacade<D, E> {

    GenericJpaService<E, Long> getService();

    D getDto(E entity) throws ServiceException;

    D getDto(E entity, Set<String> fields) throws ServiceException;

    E getEntity(D dto);

    List<D> getDtos(List<E> entities, Set<String> fields) throws ServiceException;

    List<E> getEtnties(List<D> entities);
}
