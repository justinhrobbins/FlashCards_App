package org.robbins.flashcards.repository.conversion.impl;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.BulkLoadingReceiptDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.model.BulkLoadingReceipt;
import org.robbins.flashcards.repository.util.DtoUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("bulkLoadingReceiptDtoConverter")
public class DefaultBulkLoadingReceiptDtoConverter extends AbstractDtoConverter implements DtoConverter<BulkLoadingReceiptDto, BulkLoadingReceipt> {

    @Override
    public BulkLoadingReceiptDto getDto(final BulkLoadingReceipt entity) throws RepositoryException {
        return getDto(entity, null);
    }

    @Override
    public BulkLoadingReceiptDto getDto(final BulkLoadingReceipt entity, final Set<String> fields)
            throws RepositoryException
	{
        final BulkLoadingReceiptDto dto = getMapper().map(entity, BulkLoadingReceiptDto.class);
        DtoUtil.filterFields(dto, fields);
        return dto;
    }

    @Override
    public BulkLoadingReceipt getEntity(final BulkLoadingReceiptDto dto) {
        return getMapper().map(dto, BulkLoadingReceipt.class);
    }

    @Override
    public List<BulkLoadingReceiptDto> getDtos(List<BulkLoadingReceipt> entities) throws RepositoryException {
        return getDtos(entities, null);
    }

    @Override
    public List<BulkLoadingReceiptDto> getDtos(final List<BulkLoadingReceipt> entities, final Set<String> fields)
            throws RepositoryException {

        return entities.stream()
                .map(this::getDto)
                .collect(Collectors.toList());
     }

    @Override
    public List<BulkLoadingReceipt> getEntities(final List<BulkLoadingReceiptDto> dtos) {
        List<BulkLoadingReceipt> entities = dtos.stream().map(this::getEntity).collect(Collectors.toList());
        return entities;
    }
}
