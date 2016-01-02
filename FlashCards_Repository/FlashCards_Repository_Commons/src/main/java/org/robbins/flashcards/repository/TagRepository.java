
package org.robbins.flashcards.repository;

import java.io.Serializable;
import java.util.List;

public interface TagRepository<E, ID extends Serializable> extends FlashCardsAppRepository<E, ID> {

    E findByName(final String name);
	List<E> findByFlashCards_Id(final ID flashCardId);
}
