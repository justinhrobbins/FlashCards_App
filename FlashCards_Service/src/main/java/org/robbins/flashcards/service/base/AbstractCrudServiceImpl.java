
package org.robbins.flashcards.service.base;

import java.util.List;
import java.util.Set;

import org.robbins.flashcards.exceptions.DataIntegrityException;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.springframework.dao.DataIntegrityViolationException;

public abstract class AbstractCrudServiceImpl<D> implements GenericPagingAndSortingService<D, Long>,
        CrudService<D, Long> {

    @Override
    public D save(final D entity) throws FlashcardsException {
        try {
            D result = getFacade().save(entity);
            return result;
        }
        catch (DataIntegrityViolationException integrityException)
        {
            throw new DataIntegrityException("Could NOT save '" + entity.getClass().getSimpleName() + "' due to DataIntegrityViolationException");
        }
    }

    @Override
    public Long count() {
        return getFacade().count();
    }

    @Override
    public D findOne(final Long id) throws FlashcardsException {
        return getFacade().findOne(id);
    }

	@Override
	public D findOne(final Long id, final Set<String> fields) throws FlashcardsException
	{
		return getFacade().findOne(id, fields);
	}

	@Override
    public void delete(final Long id) {
		getFacade().delete(id);
    }

    @Override
    public List<D> findAll() throws FlashcardsException {
        return getFacade().list();
    }

	@Override
	public List<D> findAll(Integer page, Integer size, String sort, String direction) throws FlashcardsException
	{
		return getFacade().list(page, size, sort, direction);
	}

	@Override
	public List<D> findAll(Integer page, Integer size, String sort, String direction,
				Set <String> fields) throws FlashcardsException {
		return getFacade().list(page, size, sort, direction, fields);
	}
}
