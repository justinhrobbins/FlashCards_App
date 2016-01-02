
package org.robbins.flashcards.service.base;

import org.robbins.flashcards.akka.AkkaBatchSavingService;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.DataIntegrityException;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

public abstract class AbstractCrudServiceImpl<D, ID extends Serializable> implements GenericPagingAndSortingService<D, ID>,
        CrudService<D, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudServiceImpl.class);

    @Inject
    private AkkaBatchSavingService batchSavingService;

    @Override
    public D save(final D dto) throws FlashCardsException
    {
        try {
            return getFacade().save(dto);
        } catch (DataIntegrityViolationException integrityException) {
            final String errorMessage = "Could NOT save '" + dto.getClass().getSimpleName() + "' due to DataIntegrityViolationException";
            LOGGER.error(errorMessage, integrityException);
            throw new DataIntegrityException(errorMessage, integrityException);
        }
    }

    @Override
    public BatchLoadingReceiptDto save(List<D> dtos) throws FlashCardsException
    {
        return batchSavingService.save(getFacade(), (List<AbstractAuditableDto>) dtos);
    }

    @Override
    public Long count() {
        return getFacade().count();
    }

    @Override
    public D findOne(final ID id) throws FlashCardsException
    {
        return getFacade().findOne(id);
    }

    @Override
    public D findOne(final ID id, final Set<String> fields) throws FlashCardsException
    {
        return getFacade().findOne(id, fields);
    }

    @Override
    public void delete(final ID id) {
        getFacade().delete(id);
    }

    @Override
    public List<D> findAll() throws FlashCardsException
    {
        return getFacade().list();
    }

    @Override
    public List<D> findAll(final Optional<Pageable> page) throws FlashCardsException
    {
        return getFacade().list(page);
    }

    @Override
    public List<D> findAll(final Optional<Pageable> page, Set<String> fields) throws FlashCardsException
    {
        return getFacade().list(page, fields);
    }

    @Override
    public List<D> findByCreatedBy(final ID userId, final Set<String> fields) throws FlashCardsException
    {
        return getFacade().findByCreatedBy(userId, fields);
    }
}
