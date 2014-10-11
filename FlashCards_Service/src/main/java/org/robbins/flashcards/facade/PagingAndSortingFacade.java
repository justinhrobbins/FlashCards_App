package org.robbins.flashcards.facade;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;

public interface PagingAndSortingFacade<D, E> {

	GenericPagingAndSortingService<E, Long> getService();
    DtoConverter<D, E> getConverter();
}
