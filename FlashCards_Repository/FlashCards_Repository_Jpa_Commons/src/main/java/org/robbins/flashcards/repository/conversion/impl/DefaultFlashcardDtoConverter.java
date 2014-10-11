package org.robbins.flashcards.repository.conversion.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.repository.util.DtoUtil;
import org.springframework.stereotype.Component;

@Component("flashcardDtoConverter")
public class DefaultFlashcardDtoConverter extends AbstractDtoConverter implements DtoConverter<FlashCardDto, FlashCard> {
    @Override
    public FlashCardDto getDto(final FlashCard entity) throws RepositoryException {
        return getDto(entity, null);
    }

    @Override
    public FlashCardDto getDto(final FlashCard entity, final Set<String> fields)
            throws RepositoryException
	{
        FlashCardDto flashCardDto = getMapper().map(entity, FlashCardDto.class);
        DtoUtil.filterFields(flashCardDto, fields);
        return flashCardDto;
    }

    @Override
    public FlashCard getEntity(final FlashCardDto dto) {
        return getMapper().map(dto, FlashCard.class);
    }

    @Override
    public List<FlashCardDto> getDtos(List<FlashCard> entities) throws RepositoryException {
        return getDtos(entities, null);
    }

    @Override
    public List<FlashCardDto> getDtos(final List<FlashCard> entities,
                                      final Set<String> fields)
            throws RepositoryException {
        List<FlashCardDto> dtos = new ArrayList<>();
        for (FlashCard entity : entities) {
            dtos.add(getDto(entity, fields));
        }
        return dtos;
    }

    @Override
    public List<FlashCard> getEtnties(final List<FlashCardDto> dtos) {
        List<FlashCard> entities = new ArrayList<>();
        for (FlashCardDto dto : dtos) {
            entities.add(getEntity(dto));
        }
        return entities;
    }
}
