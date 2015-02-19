package org.robbins.flashcards.repository.facade;

import java.io.Serializable;

import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.robbins.flashcards.conversion.DtoConverter;


public interface RepositoryFacade<D, E, ID extends Serializable> {

	FlashCardsAppRepository<E, ID> getRepository();
    DtoConverter<D, E> getConverter();
}
