
package org.robbins.flashcards.service.base;

import org.robbins.flashcards.exceptions.DataIntegrityException;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public abstract class AbstractCrudServiceImpl<D, ID extends Serializable> implements GenericPagingAndSortingService<D, ID>,
        CrudService<D, ID> {

    @Override
    public D save(final D dto) throws FlashcardsException {
        try {
            D result = getFacade().save(dto);
            return result;
        }
        catch (DataIntegrityViolationException integrityException)
        {
            throw new DataIntegrityException("Could NOT save '" + dto.getClass().getSimpleName() + "' due to DataIntegrityViolationException");
        }
    }

    @Override
    public void save(List<D> entities) throws FlashcardsException {
        for (D entity : entities) {
            save(entity);
        }
    }

    @Override
    public Long count() {
        return getFacade().count();
    }

    @Override
    public D findOne(final ID id) throws FlashcardsException {
        return getFacade().findOne(id);
    }

	@Override
	public D findOne(final ID id, final Set<String> fields) throws FlashcardsException
	{
		return getFacade().findOne(id, fields);
	}

	@Override
    public void delete(final ID id) {
		getFacade().delete(id);
    }

    @Override
    public List<D> findAll() throws FlashcardsException {
        return getFacade().list();
    }

	@Override
	public List<D> findAll(final Integer page, final Integer size, final String sort, final String direction) throws FlashcardsException
	{
		return getFacade().list(page, size, sort, direction);
	}

	@Override
	public List<D> findAll(final Integer page, final Integer size, final String sort, final String direction,
				Set <String> fields) throws FlashcardsException {
		return getFacade().list(page, size, sort, direction, fields);
	}

    @Override
    public List<D> findByCreatedBy(final ID userId, final Set<String> fields) throws FlashcardsException {
        return getFacade().findByCreatedBy(userId, fields);
    }
}
