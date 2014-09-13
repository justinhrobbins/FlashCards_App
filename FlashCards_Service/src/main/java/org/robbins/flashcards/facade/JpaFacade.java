package org.robbins.flashcards.facade;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.service.base.GenericJpaService;

public interface JpaFacade<D, E> {

    GenericJpaService<E, Long> getService();
    DtoConverter<D, E> getConverter();
}
