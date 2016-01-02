
package org.robbins.flashcards.cassandra.repository.facade;

import javax.inject.Inject;

import org.robbins.flashcards.cassandra.repository.domain.BatchLoadingReceiptCassandraEntity;
import org.robbins.flashcards.cassandra.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.facade.BatchReceiptFacade;
import org.robbins.flashcards.repository.BatchLoadingReceiptRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("batchReceiptRepositoryFacade")
public class DefaultBatchReceiptRepositoryFacade extends AbstractCrudRepositoryFacadeImpl<BatchLoadingReceiptDto, BatchLoadingReceiptCassandraEntity> implements
        BatchReceiptFacade {

    @Inject
	private BatchLoadingReceiptRepository<BatchLoadingReceiptCassandraEntity, Long> repository;

    @Inject
    @Qualifier("batchReceiptDtoConverter")
    private DtoConverter<BatchLoadingReceiptDto, BatchLoadingReceiptCassandraEntity> converter;

    @Override
    public DtoConverter<BatchLoadingReceiptDto, BatchLoadingReceiptCassandraEntity> getConverter()
    {
        return converter;
    }

    @Override
	public BatchLoadingReceiptRepository<BatchLoadingReceiptCassandraEntity, Long> getRepository() {
		return repository;
	}

}
