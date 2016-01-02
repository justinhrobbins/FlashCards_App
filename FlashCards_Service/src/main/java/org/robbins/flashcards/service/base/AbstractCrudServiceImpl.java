
package org.robbins.flashcards.service.base;

import org.robbins.flashcards.akka.AkkaBatchSavingService;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.DataIntegrityException;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public abstract class AbstractCrudServiceImpl<D, ID extends Serializable> implements GenericPagingAndSortingService<D, ID>,
        CrudService<D, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudServiceImpl.class);

    @Inject
    private AkkaBatchSavingService batchSavingService;

    @Override
    public D save(final D dto) throws FlashcardsException {
        try {
            return getFacade().save(dto);
        } catch (DataIntegrityViolationException integrityException) {
            final String errorMessage = "Could NOT save '" + dto.getClass().getSimpleName() + "' due to DataIntegrityViolationException";
            LOGGER.error(errorMessage, integrityException);
            throw new DataIntegrityException(errorMessage, integrityException);
        }
    }

    @Override
    public BatchLoadingReceiptDto save(List<D> dtos) throws FlashcardsException {
        return batchSavingService.save(getFacade(), (List<AbstractAuditableDto>) dtos);
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
    public D findOne(final ID id, final Set<String> fields) throws FlashcardsException {
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
    public List<D> findAll(final Integer page, final Integer size, final String sort, final String direction) throws FlashcardsException {
        return getFacade().list(page, size, sort, direction);
    }

    @Override
    public List<D> findAll(final Integer page, final Integer size, final String sort, final String direction,
                           Set<String> fields) throws FlashcardsException {
        return getFacade().list(page, size, sort, direction, fields);
    }

    @Override
    public List<D> findByCreatedBy(final ID userId, final Set<String> fields) throws FlashcardsException {
        return getFacade().findByCreatedBy(userId, fields);
    }
}
