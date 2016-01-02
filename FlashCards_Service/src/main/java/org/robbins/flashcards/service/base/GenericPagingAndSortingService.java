
package org.robbins.flashcards.service.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.robbins.flashcards.exceptions.FlashCardsException;
import org.springframework.data.domain.Pageable;

public interface GenericPagingAndSortingService<D, ID extends Serializable> extends
		GenericCrudService<D, ID>
{
	List<D> findAll(final Optional<Pageable> page)
			throws FlashCardsException;

	List<D> findAll(final Optional<Pageable> page,
			Set<String> fields) throws FlashCardsException;

	D findOne(ID id, Set<String> fields) throws FlashCardsException;

	List<D> findByCreatedBy(final ID userId, final Set<String> fields) throws FlashCardsException;
}
