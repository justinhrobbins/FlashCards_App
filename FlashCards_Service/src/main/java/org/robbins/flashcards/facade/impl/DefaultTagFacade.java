package org.robbins.flashcards.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.TagService;
import org.springframework.stereotype.Component;

@Component
public class DefaultTagFacade extends AbstractCrudFacadeImpl<TagDto, Tag> implements TagFacade {
	
	@Inject
	private TagService service;

	public TagService getService() {
		return service;
	}
	
	public TagDto findByName(String name) {
		Tag result = service.findByName(name);
		TagDto tagDto = getMapper().map(result, TagDto.class);
		return tagDto;
	}

	@Override
	public TagDto getDto(Tag entity) {
		return getMapper().map(entity, TagDto.class);
	}

	@Override
	public Tag getEntity(TagDto dto) {
		return getMapper().map(dto, Tag.class);
	}

	@Override
	public List<TagDto> getDtos(List<Tag> entities) {
		List<TagDto> dtos = new ArrayList<TagDto>();
		for (Tag entity : entities){
			dtos.add(getDto(entity));
		}
		return dtos;
	}

	@Override
	public List<Tag> getEtnties(List<TagDto> dtos) {
		List<Tag> entities = new ArrayList<Tag>();
		for (TagDto dto : dtos){
			entities.add(getEntity(dto));
		}
		return entities;
	}
}
