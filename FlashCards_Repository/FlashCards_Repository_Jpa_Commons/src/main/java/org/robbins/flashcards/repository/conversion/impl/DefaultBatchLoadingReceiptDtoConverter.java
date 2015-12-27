package org.robbins.flashcards.repository.conversion.impl;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.model.BatchLoadingReceipt;
import org.robbins.flashcards.repository.util.DtoUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("batchLoadingReceiptDtoConverter")
public class DefaultBatchLoadingReceiptDtoConverter extends AbstractDtoConverter implements DtoConverter<BatchLoadingReceiptDto, BatchLoadingReceipt> {

    @Override
    public BatchLoadingReceiptDto getDto(final BatchLoadingReceipt entity) throws RepositoryException {
        return getDto(entity, null);
    }

    @Override
    public BatchLoadingReceiptDto getDto(final BatchLoadingReceipt entity, final Set<String> fields)
            throws RepositoryException
	{
        final BatchLoadingReceiptDto dto = getMapper().map(entity, BatchLoadingReceiptDto.class);
        DtoUtil.filterFields(dto, fields);
        return dto;
    }

    @Override
    public BatchLoadingReceipt getEntity(final BatchLoadingReceiptDto dto) {
        return getMapper().map(dto, BatchLoadingReceipt.class);
    }

    @Override
    public List<BatchLoadingReceiptDto> getDtos(List<BatchLoadingReceipt> entities) throws RepositoryException {
        return getDtos(entities, null);
    }

    @Override
    public List<BatchLoadingReceiptDto> getDtos(final List<BatchLoadingReceipt> entities, final Set<String> fields)
            throws RepositoryException {

        return entities.stream()
                .map(this::getDto)
                .collect(Collectors.toList());
     }

    @Override
    public List<BatchLoadingReceipt> getEntities(final List<BatchLoadingReceiptDto> dtos) {
        List<BatchLoadingReceipt> entities = dtos.stream().map(this::getEntity).collect(Collectors.toList());
        return entities;
    }
}
