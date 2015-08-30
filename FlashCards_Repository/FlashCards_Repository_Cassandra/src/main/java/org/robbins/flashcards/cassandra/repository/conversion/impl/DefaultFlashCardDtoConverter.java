package org.robbins.flashcards.cassandra.repository.conversion.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.repository.conversion.impl.AbstractDtoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("flashcardDtoConverter")
public class DefaultFlashCardDtoConverter extends AbstractDtoConverter implements DtoConverter<FlashCardDto, FlashCardCassandraEntity> {

    @Override
    public FlashCardDto getDto(final FlashCardCassandraEntity entity) throws RepositoryException {
        return getMapper().map(entity, FlashCardDto.class);
    }

    @Override
    public FlashCardDto getDto(final FlashCardCassandraEntity entity, final Set<String> fields)
            throws RepositoryException
	{
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public FlashCardCassandraEntity getEntity(final FlashCardDto dto) {
        return getMapper().map(dto, FlashCardCassandraEntity.class);
    }

    @Override
    public List<FlashCardDto> getDtos(List<FlashCardCassandraEntity> entities) throws RepositoryException {
        return entities.stream()
                .map(this::getDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlashCardDto> getDtos(final List<FlashCardCassandraEntity> entities, final Set<String> fields)
            throws RepositoryException {throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardCassandraEntity> getEntities(final List<FlashCardDto> dtos) {

       return dtos.stream()
                .map(this::getEntity)
                .collect(Collectors.toList());
    }
}
