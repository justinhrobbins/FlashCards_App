
package org.robbins.flashcards.repository.facade.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.repository.facade.PagingAndSortingRepositoryFacade;
import org.robbins.flashcards.repository.util.FieldInitializerUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractCrudRepositoryFacadeImpl<D, E> implements GenericCrudFacade<D>,
		PagingAndSortingRepositoryFacade<D, E, Long>
{

    @Inject
    private FieldInitializerUtil fieldInitializer;

    @Override
    public D save(final D dto) throws RepositoryException
	{
        E entity = getConverter().getEntity(dto);
        E resultEntity = getRepository().save(entity);
        return getConverter().getDto(resultEntity);
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

        // if we are explicitly requested certain fields
        // make sure they have been initialized
        if (CollectionUtils.isNotEmpty(fields)) {
            fieldInitializer.initializeEntity(entities, fields);
        }

        List<D> dtos = new ArrayList<>();
        for (E entity : entities) {
            dtos.add(getConverter().getDto(entity, fields));
        }

        return dtos;
    }

    @Override
    public Long count() {
        return getRepository().count();
    }

    @Override
    public D findOne(final Long id) throws RepositoryException {
        return findOne(id, null);
    }

    @Override
    public D findOne(final Long id, final Set<String> fields) throws RepositoryException {
        E resultEntity = getRepository().findOne(id);

        if (resultEntity == null) {
            return null;
        }

        if (CollectionUtils.isNotEmpty(fields)) {
            fieldInitializer.initializeEntity(resultEntity, fields);
        }

        return getConverter().getDto(resultEntity, fields);
    }

    @Override
    public void delete(final Long id) {
		getRepository().delete(id);
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
}
