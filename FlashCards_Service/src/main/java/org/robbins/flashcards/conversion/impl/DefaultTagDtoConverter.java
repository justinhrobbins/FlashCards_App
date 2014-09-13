package org.robbins.flashcards.conversion.impl;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.util.DtoUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("tagDtoConverter")
public class DefaultTagDtoConverter extends AbstractDtoConverter implements DtoConverter<TagDto, Tag> {

    @Override
    public TagDto getDto(final Tag entity) throws ServiceException {
        return getDto(entity, null);
    }

    @Override
    public TagDto getDto(final Tag entity, final Set<String> fields)
            throws ServiceException {
        TagDto tagDto = getMapper().map(entity, TagDto.class);
        DtoUtil.filterFields(tagDto, fields);
        return tagDto;
    }

    @Override
    public Tag getEntity(final TagDto dto) {
        return getMapper().map(dto, Tag.class);
    }

    @Override
    public List<TagDto> getDtos(final List<Tag> entities, final Set<String> fields)
            throws ServiceException {
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
