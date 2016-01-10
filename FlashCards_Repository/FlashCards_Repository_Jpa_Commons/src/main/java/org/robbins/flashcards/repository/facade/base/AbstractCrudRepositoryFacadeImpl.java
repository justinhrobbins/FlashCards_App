
package org.robbins.flashcards.repository.facade.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.model.util.EntityAuditingUtil;
import org.robbins.flashcards.repository.auditing.AuditingAwareUser;
import org.robbins.flashcards.repository.facade.RepositoryFacade;
import org.robbins.flashcards.repository.util.FieldInitializerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractCrudRepositoryFacadeImpl<D, E extends AbstractAuditable, ID extends Serializable> implements GenericCrudFacade<D, ID>,
        RepositoryFacade<D, E, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudRepositoryFacadeImpl.class);

    @Inject
    private FieldInitializerUtil fieldInitializer;

    @Inject
    private AuditingAwareUser auditorAware;

    public Long getAuditingUserId() {
        return auditorAware.getCurrentAuditor();
    }

    @Override
    public D save(final D dto) throws RepositoryException {
        final E entity = getConverter().getEntity(dto);
        EntityAuditingUtil.configureCreatedByAndTime(entity, getAuditingUserId());
        final E result = getRepository().save(entity);
        return convertAndInitializeEntity(result);
    }

    @Transactional(propagation= Propagation.NEVER)
    @Override
    public BatchLoadingReceiptDto save(final List<D> dtos) throws FlashCardsException
    {
        if (CollectionUtils.isEmpty(dtos))
            throw new FlashCardsException("Expected list with at least one element");

        final List<E> entities = getConverter().getEntities(dtos);
        final int count = getRepository().batchSave(entities);
        final BatchLoadingReceiptDto result = new BatchLoadingReceiptDto();
        result.setSuccessCount(count);

        return result;
    }

    @Override
    public List<D> list() throws RepositoryException {
        return list(Optional.empty());
    }

    @Override
    public List<D> list(final Optional<Pageable> page)
            throws RepositoryException {
        return this.list(page, null);
    }

    @Override
    public List<D> list(final Optional<Pageable> page, final Set<String> fields) throws RepositoryException {

        final List<E> entities;

        if (page.isPresent()) {
            entities = getRepository().findAll(page.get()).getContent();
        }
        else {
            entities = getRepository().findAll();
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
        final E result = getRepository().findOne(id);

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
    public List<D> findByCreatedBy(final ID userId, final Set<String> fields) throws FlashCardsException
    {
        final List<E> results = getRepository().findByCreatedBy_Id(userId);
        return convertAndInitializeEntities(results, fields);
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
