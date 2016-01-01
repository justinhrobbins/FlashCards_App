
package org.robbins.flashcards.repository.facade.base;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.model.BatchLoadingReceipt;
import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.model.util.AuditingUtil;
import org.robbins.flashcards.repository.BatchLoadingReceiptRepository;
import org.robbins.flashcards.akka.AkkaBatchSavingService;
import org.robbins.flashcards.repository.auditing.AuditingAwareUser;
import org.robbins.flashcards.repository.facade.RepositoryFacade;
import org.robbins.flashcards.repository.util.FieldInitializerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Transactional
public abstract class AbstractCrudRepositoryFacadeImpl<D, E, ID extends Serializable> implements GenericCrudFacade<D, ID>,
        RepositoryFacade<D, E, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudRepositoryFacadeImpl.class);

    @Inject
    private FieldInitializerUtil fieldInitializer;

    @Inject
    private AkkaBatchSavingService batchSavingService;

    @Inject
    private BatchLoadingReceiptRepository<BatchLoadingReceipt, Long> receiptRepository;

    @Inject
    @Qualifier("batchLoadingReceiptDtoConverter")
    private DtoConverter<BatchLoadingReceiptDto, BatchLoadingReceipt> converter;

    @Inject
    private AuditingAwareUser auditorAware;

    public Long getAuditingUserId() {
        return auditorAware.getCurrentAuditor();
    }

    @Override
    public D save(final D dto) throws RepositoryException {
        E entity = getConverter().getEntity(dto);
        AuditingUtil.configureCreatedByAndTime((AbstractAuditable) entity, getAuditingUserId());
        E result = getRepository().save(entity);
        return convertAndInitializeEntity(result);
    }

    @Transactional(propagation= Propagation.NEVER)
    @Override
    public BatchLoadingReceiptDto save(final List<D> dtos) throws FlashcardsException {
        if (CollectionUtils.isEmpty(dtos))
            throw new FlashcardsException("Expected list with at least one element");

        return batchSavingService.save(getRepository(), getConverter(), getAuditingUserId(), (List<AbstractPersistableDto>) dtos);
    }

//    @Override
//    public BatchLoadingReceiptDto save(List<D> entities) throws FlashcardsException {
//        if (CollectionUtils.isEmpty(entities))
//            throw new FlashcardsException("Expected list with at least one element");
//
//        //TODO Receipt should be persisted prior to persisting entities, may need to adjust transaction boundaries
//        BatchLoadingReceipt receipt = createBatchLoadingReceipt(entities.get(0).getClass().getSimpleName());
//
//        // TODO Handle failures
//        entities.forEach(this::save);
//
//        receipt = completeBatchLoadingReceipt(entities.size(), 0, receipt);
//        LOGGER.trace(receipt.toString());
//        return converter.getDto(receipt);
//    }
//
//    private BatchLoadingReceipt createBatchLoadingReceipt(final String type) {
//        BatchLoadingReceipt receipt = new BatchLoadingReceipt();
//        receipt.setType(type);
//        receipt.setStartTime(new Date());
//        AuditingUtil.configureCreatedByAndTime(receipt, getAuditingUserId());
//        return receiptRepository.save(receipt);
//    }
//
//    private BatchLoadingReceipt completeBatchLoadingReceipt(final int successCount, final int failureCount, final BatchLoadingReceipt receipt) {
//        receipt.setSuccessCount(successCount);
//        receipt.setFailureCount(failureCount);
//        receipt.setEndTime(new Date());
//        return receiptRepository.save(receipt);
//    }

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
