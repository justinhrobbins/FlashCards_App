package org.robbins.flashcards.client;

import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;

import java.net.IDN;
import java.util.Map;

public interface GenericRestCrudFacade<E, ID> extends GenericCrudFacade<E, ID>, GenericClient {

    void put(E entity) throws ServiceException;
    void update(E entity) throws ServiceException;
    E searchSingleEntity(final Map<String, String> uriVariables);
    E[] searchEntities(final String url, final Map<String, String> uriVariables,
                       final Class<E[]> clazz);
}
