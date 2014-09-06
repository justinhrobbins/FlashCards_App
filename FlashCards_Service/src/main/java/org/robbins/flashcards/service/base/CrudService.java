
package org.robbins.flashcards.service.base;

import java.io.Serializable;

import org.robbins.flashcards.repository.FlashCardsAppRepository;

public interface CrudService<T, ID extends Serializable> {

    FlashCardsAppRepository<T, ID> getRepository();
}
