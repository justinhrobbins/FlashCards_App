
package org.robbins.flashcards.cassandra.repository.facade.base;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.AbstractPersistable;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.repository.facade.RepositoryFacade;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractCrudRepositoryFacadeImpl<D, E extends AbstractPersistable> implements GenericCrudFacade<D, String>,
        RepositoryFacade<D, E, UUID>
{
    @Override
    public List<D> list() throws FlashcardsException {
        return list(null, null, null, null);
    }

    @Override
    public List<D> list(final Integer page, final Integer size, final String sort, final String direction) throws FlashcardsException {
        return list(null, null, null, null, null);
    }

    @Override
    public List<D> list(final Integer page, final Integer size, final String sort, final String direction, final Set<String> fields) throws FlashcardsException {
        List<E> results = Lists.newArrayList(getRepository().findAll());
        return getConverter().getDtos(results);
    }

    @Override
    public Long count() {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public D findOne(final String id) throws FlashcardsException {
        return findOne(id, null);
    }

    @Override
    public D findOne(final String id, final Set<String> fields) throws FlashcardsException {
        E result = getRepository().findOne(UUID.fromString(id));
        return result == null ? null : getConverter().getDto(result);
    }

    @Override
    public D save(final D dto) throws FlashcardsException {
        E entity = getConverter().getEntity(dto);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        E result = getRepository().save(entity);
        return getConverter().getDto(result);
    }

    @Override
    public void save(List<D> entities) throws FlashcardsException {
        for (D entity : entities) {
            save(entity);
        }
    }

    @Override
    public void delete(final String id) {
        getRepository().delete(UUID.fromString(id));
    }

    @Override
    public List<D> findByCreatedBy(final String userId, final Set<String> fields) throws FlashcardsException {
        return null;
    }
}
