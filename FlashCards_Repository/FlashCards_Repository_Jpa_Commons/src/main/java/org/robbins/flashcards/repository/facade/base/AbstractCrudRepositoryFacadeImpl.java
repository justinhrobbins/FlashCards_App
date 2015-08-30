
package org.robbins.flashcards.repository.facade.base;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.repository.facade.RepositoryFacade;
import org.robbins.flashcards.repository.util.FieldInitializerUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Transactional
public abstract class AbstractCrudRepositoryFacadeImpl<D, E, ID extends Serializable> implements GenericCrudFacade<D, ID>,
        RepositoryFacade<D, E, ID>
{

    @Inject
    private FieldInitializerUtil fieldInitializer;

    @Override
    public D save(final D dto) throws RepositoryException
	{
        E entity = getConverter().getEntity(dto);
        E result = getRepository().save(entity);
        return convertAndInitializeEntity(result);
    }

    @Override
    public void save(List<D> entities) throws FlashcardsException {
        entities.forEach(this::save);
    }

    @Override
    public List<D> list() throws RepositoryException {
        return list(null, null, null, null);
    }

    @Override
    public List<D> list(final Integer page, final Integer size, final String sort,
            final String direction)
            throws RepositoryException {
        return this.list(page, size, sort, direction, null);
    }

    @Override
    public List<D> list(final Integer page, final Integer size, final String sort,
            final String direction, final Set<String> fields) throws RepositoryException {

        List<E> entities = null;

        // are we trying to use Pagination or Sorting?
        // if not then go ahead and return findAll()
        if ((page == null) && (StringUtils.isEmpty(sort))) {
            entities = getRepository().findAll();
        } // should we Page
        else if (page != null) {
            PageRequest pageRequest = getPageRequest(page, size, sort, direction);
            entities = getRepository().findAll(pageRequest).getContent();
        } // should we just Sort the list?
        else if (!StringUtils.isEmpty(sort)) {
            // get a sorted list
            Sort entitySort = getSort(sort, direction);
            entities = getRepository().findAll(entitySort);
        }

        if (CollectionUtils.isEmpty(entities)) {
            return null;
        }

        return convertAndInitializeEntities(entities, fields);
    }

    @Override
    public Long count() {
        return getRepository().count();
    }

    @Override
    public D findOne(final ID id) throws RepositoryException {
        return findOne(id, null);
    }

    @Override
    public D findOne(final ID id, final Set<String> fields) throws RepositoryException {
        E result = getRepository().findOne(id);

        if (result == null) {
            return null;
        }
        return convertAndInitializeEntity(result, fields);
    }

    @Override
    public void delete(final ID id) {
		getRepository().delete(id);
    }

    @Override
    public List<D> findByCreatedBy(final ID userId, final Set<String> fields) throws FlashcardsException {
        List<E> results = getRepository().findByCreatedBy_Id(userId);
        return convertAndInitializeEntities(results, fields);
    }

    protected PageRequest getPageRequest(final Integer page, final Integer size,
            final String sortOrder, final String sortDirection) {
        // are we Sorting too?
        if (!StringUtils.isEmpty(sortOrder)) {
            Sort sort = getSort(sortOrder, sortDirection);
            return new PageRequest(page, size, sort);
        } else {
            return new PageRequest(page, size);
        }
    }

    protected Sort getSort(final String sort, final String order) {
        if ((StringUtils.isEmpty(order)) || (order.equals("asc"))) {
            return new Sort(Direction.ASC, order);
        } else if (order.equals("desc")) {
            return new Sort(Direction.DESC, order);
        } else {
            throw new IllegalArgumentException("Sort order must be 'asc' or 'desc'.  '"
                    + order + "' is not an acceptable sort order");
        }
    }

    protected List<D> convertAndInitializeEntities(final List<E> entities) throws RepositoryException {
        return convertAndInitializeEntities(entities, null);
    }

    protected List<D> convertAndInitializeEntities(final List<E> entities, final Set<String> fields) throws RepositoryException {
        if (CollectionUtils.isNotEmpty(fields)) {
            fieldInitializer.initializeEntity(entities, fields);
        }
        return getConverter().getDtos(entities, fields);
    }

    protected D convertAndInitializeEntity(final E entity, final Set<String> fields) throws RepositoryException {
        if (CollectionUtils.isNotEmpty(fields)) {
            fieldInitializer.initializeEntity(entity, fields);
        }
        return getConverter().getDto(entity, fields);
    }

    protected D convertAndInitializeEntity(final E entity) throws RepositoryException {
        return convertAndInitializeEntity(entity, null);
    }
}
