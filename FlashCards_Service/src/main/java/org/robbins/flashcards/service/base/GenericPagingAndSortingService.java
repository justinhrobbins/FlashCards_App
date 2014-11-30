
package org.robbins.flashcards.service.base;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.robbins.flashcards.exceptions.FlashcardsException;

public interface GenericPagingAndSortingService<D, ID extends Serializable> extends
        GenericCrudService<D, ID> {

	List<D> findAll(Integer page, Integer size, String sort, String direction)
			throws FlashcardsException;

	List<D> findAll(Integer page, Integer size, String sort, String direction,
			Set<String> fields) throws FlashcardsException;

	D findOne(ID id, Set<String> fields) throws FlashcardsException;

	List<D> findByCreatedBy(final Long userId, final Set<String> fields) throws FlashcardsException;
}
