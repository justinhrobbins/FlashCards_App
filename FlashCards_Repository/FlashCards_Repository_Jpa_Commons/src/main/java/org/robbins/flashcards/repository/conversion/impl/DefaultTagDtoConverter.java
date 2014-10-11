package org.robbins.flashcards.repository.conversion.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.repository.util.DtoUtil;
import org.springframework.stereotype.Component;

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
        TagDto tagDto = getMapper().map(entity, TagDto.class);
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
        List<TagDto> dtos = new ArrayList<>();
        for (Tag entity : entities) {
            dtos.add(getDto(entity, fields));
        }
        return dtos;
    }

    @Override
    public List<Tag> getEtnties(final List<TagDto> dtos) {
        List<Tag> entities = new ArrayList<>();
        for (TagDto dto : dtos) {
            entities.add(getEntity(dto));
        }
        return entities;
    }
}
