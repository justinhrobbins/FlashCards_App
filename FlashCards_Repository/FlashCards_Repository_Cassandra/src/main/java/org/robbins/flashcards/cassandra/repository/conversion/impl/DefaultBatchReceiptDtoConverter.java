package org.robbins.flashcards.cassandra.repository.conversion.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.BatchLoadingReceiptCassandraEntity;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.repository.conversion.impl.AbstractDtoConverter;
import org.springframework.stereotype.Component;

@Component("batchReceiptDtoConverter")
public class DefaultBatchReceiptDtoConverter extends AbstractDtoConverter implements DtoConverter<BatchLoadingReceiptDto, BatchLoadingReceiptCassandraEntity> {

    @Override
    public BatchLoadingReceiptDto getDto(final BatchLoadingReceiptCassandraEntity entity) throws RepositoryException {
        return getMapper().map(entity, BatchLoadingReceiptDto.class);
    }

    @Override
    public BatchLoadingReceiptDto getDto(final BatchLoadingReceiptCassandraEntity entity, final Set<String> fields)
            throws RepositoryException
	{
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public BatchLoadingReceiptCassandraEntity getEntity(final BatchLoadingReceiptDto dto) {
        return getMapper().map(dto, BatchLoadingReceiptCassandraEntity.class);
    }

    @Override
    public List<BatchLoadingReceiptDto> getDtos(List<BatchLoadingReceiptCassandraEntity> entities) throws RepositoryException {
        return entities.stream().map(this::getDto).collect(Collectors.toList());
    }

    @Override
    public List<BatchLoadingReceiptDto> getDtos(final List<BatchLoadingReceiptCassandraEntity> entities, final Set<String> fields)
            throws RepositoryException {throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<BatchLoadingReceiptCassandraEntity> getEntities(final List<BatchLoadingReceiptDto> dtos) {
        return dtos.stream()
                .map(this::getEntity)
                .collect(Collectors.toList());
    }
}
