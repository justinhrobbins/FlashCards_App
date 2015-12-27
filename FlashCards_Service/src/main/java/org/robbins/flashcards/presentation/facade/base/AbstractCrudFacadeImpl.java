
package org.robbins.flashcards.presentation.facade.base;

import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.presentation.facade.PagingAndSortingFacade;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public abstract class AbstractCrudFacadeImpl<D, ID extends Serializable> implements GenericCrudFacade<D, ID>, PagingAndSortingFacade<D, ID>
{
    @Override
    public D save(final D dto) throws FlashcardsException {
        return getService().save(dto);
    }

    @Override
    public BatchLoadingReceiptDto save(List<D> entities) throws FlashcardsException {
        return getService().save(entities);
    }

    @Override
    public List<D> list() throws FlashcardsException {
        return list(null, null, null, null);
    }

    @Override
    public List<D> list(final Integer page, final Integer size, final String sort,
            final String direction)
            throws FlashcardsException {
        return this.list(page, size, sort, direction, null);
    }

    @Override
    public List<D> list(final Integer page, final Integer size, final String sort,
            final String direction, final Set<String> fields) throws FlashcardsException {

		return getService().findAll(page, size, sort, direction, fields);
    }

    @Override
    public Long count() {
        return getService().count();
    }

    @Override
    public D findOne(final ID id) throws FlashcardsException {
        return findOne(id, null);
    }

    @Override
    public D findOne(final ID id, final Set<String> fields) throws FlashcardsException {
        return getService().findOne(id, fields);
    }

    @Override
    public void delete(final ID id) {
        getService().delete(id);
    }

    @Override
    public List<D> findByCreatedBy(final ID userId, Set<String> fields) throws FlashcardsException {
        return getService().findByCreatedBy(userId, fields);
    }
}
