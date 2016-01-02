
package org.robbins.flashcards.repository.facade.impl;

import javax.inject.Inject;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.facade.BatchReceiptFacade;
import org.robbins.flashcards.model.BatchLoadingReceipt;
import org.robbins.flashcards.repository.BatchLoadingReceiptRepository;
import org.robbins.flashcards.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("batchReceiptRepositoryFacade")
public class DefaultBatchReceiptRepositoryFacade extends AbstractCrudRepositoryFacadeImpl<BatchLoadingReceiptDto, BatchLoadingReceipt, Long> implements
        BatchReceiptFacade {

    @Inject
	private BatchLoadingReceiptRepository<BatchLoadingReceipt, Long> repository;

    @Inject
    @Qualifier("batchLoadingReceiptDtoConverter")
    private DtoConverter<BatchLoadingReceiptDto, BatchLoadingReceipt> converter;

    @Override
    public DtoConverter<BatchLoadingReceiptDto, BatchLoadingReceipt> getConverter()
    {
        return converter;
    }

    @Override
	public BatchLoadingReceiptRepository<BatchLoadingReceipt, Long> getRepository() {
		return repository;
	}

}
