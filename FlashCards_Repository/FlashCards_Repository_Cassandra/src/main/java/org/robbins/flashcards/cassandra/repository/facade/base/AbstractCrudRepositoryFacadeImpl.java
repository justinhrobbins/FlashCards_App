
package org.robbins.flashcards.cassandra.repository.facade.base;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.AbstractPersistable;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.repository.facade.RepositoryFacade;
import org.springframework.data.domain.Pageable;

import com.datastax.driver.core.utils.UUIDs;
import com.google.common.collect.Lists;

public abstract class AbstractCrudRepositoryFacadeImpl<D, E extends AbstractPersistable> implements GenericCrudFacade<D, Long>,
        RepositoryFacade<D, E, Long>
{
    @Override
    public List<D> list() throws FlashCardsException
    {
        final List<E> results = Lists.newArrayList(getRepository().findAll());
        return getConverter().getDtos(results);
    }

    @Override
    public List<D> list(final Optional<Pageable> page) throws FlashCardsException
    {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<D> list(final Optional<Pageable> page, final Set<String> fields) throws FlashCardsException
    {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public Long count() {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public D findOne(final Long id) throws FlashCardsException
    {
        return findOne(id, null);
    }

    @Override
    public D findOne(final Long id, final Set<String> fields) throws FlashCardsException
    {
        final E result = getRepository().findOne(id);
        return result == null ? null : getConverter().getDto(result);
    }

    @Override
    public D save(final D dto) throws FlashCardsException
    {
        final E entity = getConverter().getEntity(dto);
        addId(entity);
        final E result = getRepository().save(entity);
        return getConverter().getDto(result);
    }

    @Override
    public BatchLoadingReceiptDto save(final List<D> dtos) throws FlashCardsException
    {

        if (CollectionUtils.isEmpty(dtos))
            throw new FlashCardsException("Expected list with at least one element");

        final List<E> entities = getConverter().getEntities(dtos);
        addIds(entities);
        final int count = getRepository().batchSave(entities);
        final BatchLoadingReceiptDto result = new BatchLoadingReceiptDto();
        result.setSuccessCount(count);

        return result;
    }

    private void addId(final E entity)
    {
        addIds(Arrays.asList(entity));
    }

    private void addIds(final List<E> entities)
    {
        entities.forEach(entity -> {
            if (entity.getId() == null)
            {
                entity.setId(UUIDs.timeBased().timestamp());
            }
        });
    }

    @Override
    public void delete(final Long id) {
        getRepository().delete(id);
    }

    @Override
    public List<D> findByCreatedBy(final Long userId, final Set<String> fields) throws FlashCardsException
    {
        return null;
    }
}
