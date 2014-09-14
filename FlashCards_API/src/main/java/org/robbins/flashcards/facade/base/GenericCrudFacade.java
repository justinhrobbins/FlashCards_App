
package org.robbins.flashcards.facade.base;

import java.util.List;
import java.util.Set;

import org.robbins.flashcards.exceptions.ServiceException;

import javax.xml.ws.Service;

public interface GenericCrudFacade<D> {

    List<D> list() throws ServiceException;

    List<D> list(Integer page, Integer size, String sort, String direction)
            throws ServiceException;

    List<D> list(Integer page, Integer size, String sort, String direction,
            Set<String> fields) throws ServiceException;

    Long count();

    D findOne(Long id) throws ServiceException;

    D findOne(Long id, Set<String> fields) throws ServiceException;

    D save(D entity) throws ServiceException;

    void delete(Long id);
}
