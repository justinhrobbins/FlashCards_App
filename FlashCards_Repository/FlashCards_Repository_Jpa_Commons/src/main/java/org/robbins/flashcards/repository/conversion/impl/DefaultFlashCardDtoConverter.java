package org.robbins.flashcards.repository.conversion.impl;


import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.repository.util.DtoUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("flashcardDtoConverter")
public class DefaultFlashCardDtoConverter extends AbstractDtoConverter implements DtoConverter<FlashCardDto, FlashCard> {
    @Override
    public FlashCardDto getDto(final FlashCard entity) throws RepositoryException {
        return getDto(entity, null);
    }

    @Override
    public FlashCardDto getDto(final FlashCard entity, final Set<String> fields)
            throws RepositoryException
	{
        final FlashCardDto flashCardDto = getMapper().map(entity, FlashCardDto.class);
        DtoUtil.filterFields(flashCardDto, fields);
        return flashCardDto;
    }

    @Override
    public FlashCard getEntity(final FlashCardDto dto) {
        return getMapper().map(dto, FlashCard.class);
    }

    @Override
    public List<FlashCardDto> getDtos(final List<FlashCard> entities) throws RepositoryException {
        return getDtos(entities, null);
    }

    @Override
    public List<FlashCardDto> getDtos(final List<FlashCard> entities,
                                      final Set<String> fields)
            throws RepositoryException {

        return entities.stream()
                .map(entity -> getDto(entity, fields))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlashCard> getEntities(final List<FlashCardDto> dtos) {
        return dtos.stream()
                .map(this::getEntity)
                .collect((Collectors.toList()));
    }
}
