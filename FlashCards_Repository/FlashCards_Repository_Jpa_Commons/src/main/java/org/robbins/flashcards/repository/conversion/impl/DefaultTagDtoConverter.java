package org.robbins.flashcards.repository.conversion.impl;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.util.DtoUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("tagDtoConverter")
public class DefaultTagDtoConverter extends AbstractDtoConverter implements DtoConverter<TagDto, Tag> {

    @Override
    public TagDto getDto(final Tag entity) throws RepositoryException {
        return getDto(entity, null);
    }

    @Override
    public TagDto getDto(final Tag entity, final Set<String> fields)
            throws RepositoryException
	{
        final TagDto tagDto = getMapper().map(entity, TagDto.class);
        DtoUtil.filterFields(tagDto, fields);
        return tagDto;
    }

    @Override
    public Tag getEntity(final TagDto dto) {
        return getMapper().map(dto, Tag.class);
    }

    @Override
    public List<TagDto> getDtos(List<Tag> entities) throws RepositoryException {
        return getDtos(entities, null);
    }

    @Override
    public List<TagDto> getDtos(final List<Tag> entities, final Set<String> fields)
            throws RepositoryException {

        return entities.stream()
                .map(entity -> getDto(entity, fields))
                .collect(Collectors.toList());
     }

    @Override
    public List<Tag> getEntities(final List<TagDto> dtos) {
        return dtos.stream().map(this::getEntity).collect(Collectors.toList());
    }
}
