package org.robbins.flashcards.facade.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.facade.base.AbstractCrudFacadeImpl;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.FlashCardService;
import org.robbins.flashcards.service.TagService;
import org.robbins.flashcards.service.util.DtoUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Component
public class DefaultFlashcardFacade extends AbstractCrudFacadeImpl<FlashCardDto, FlashCard> implements FlashcardFacade {
	
	@Inject
	private FlashCardService flashcardService;

	@Inject
	private TagFacade tagFacade;
	
	@Inject
	private TagService tagService;
	
	@Override
	public FlashCardService getService() {
		return flashcardService;
	}

	@Override
	public FlashCardDto save(FlashCardDto dto) throws ServiceException {
		FlashCard entity = getEntity(dto);
		entity.setTags(configureTags(dto.getTags()));
		FlashCard resultEntity = getService().save(entity);
		FlashCardDto resultDto = getDto(resultEntity);
		return resultDto;
	}
	
	public List<FlashCardDto> findByTagsIn(Set<TagDto> tagDtos) throws ServiceException {
		return getDtos(flashcardService.findByTagsIn(getTags(tagDtos)), null);
	}
	public List<FlashCardDto> findByTagsIn(Set<TagDto> tagDtos, PageRequest page) throws ServiceException {
		return getDtos(flashcardService.findByTagsIn(getTags(tagDtos), page), null);
	}
	public List<FlashCardDto> findByQuestionLike(String question) throws ServiceException {
		return getDtos(flashcardService.findByQuestionLike(question), null);
	}
	public List<FlashCardDto> findByQuestionLike(String question, PageRequest page) throws ServiceException {
		return getDtos(flashcardService.findByQuestionLike(question, page), null);
	}
	public FlashCardDto findByQuestion(String question) throws ServiceException {
		return getDto(flashcardService.findByQuestion(question));
	}

	private Set<Tag> getTags(Set<TagDto> tagDtos) {
		List<TagDto> tagDtoList = Lists.newArrayList(tagDtos);
		List<Tag> tagList = tagFacade.getEtnties(tagDtoList);
		return Sets.newHashSet(tagList);
	}

	@Override
	public FlashCardDto getDto(FlashCard entity) throws ServiceException {
		return getDto(entity, null);
	}
	
	@Override
	public FlashCardDto getDto(FlashCard entity, Set<String> fields) throws ServiceException {
		FlashCardDto flashCardDto = getMapper().map(entity, FlashCardDto.class);
		DtoUtil.filterFields(flashCardDto, fields);
		return flashCardDto;
	}

	@Override
	public FlashCard getEntity(FlashCardDto dto) {
		return getMapper().map(dto, FlashCard.class);
	}
	
	@Override
	public List<FlashCardDto> getDtos(List<FlashCard> entities, Set<String> fields) throws ServiceException {
		List<FlashCardDto> dtos = new ArrayList<FlashCardDto>();
		for (FlashCard entity : entities){
			dtos.add(getDto(entity));
		}
		return dtos;
	}

	@Override
	public List<FlashCard> getEtnties(List<FlashCardDto> dtos) {
		List<FlashCard> entities = new ArrayList<FlashCard>();
		for (FlashCardDto dto : dtos){
			entities.add(getEntity(dto));
		}
		return entities;
	}

	private Set<Tag> configureTags(Set<TagDto> tags) {

		Set<Tag> results = new HashSet<Tag>();
		for (TagDto tagDto : tags) {
			// if we don't have the id of the Tag
			if (tagDto.getId() == null || tagDto.getId() == 0) {
				// try to get the existing Tag
				Tag existingTag = tagService.findByName(tagDto.getName());
				
				// does the Tag exist?
				if (existingTag != null) {
					results.add(existingTag);
				} else {
					// tag doesn't exist, re-add the Tag as-as
					results.add(tagFacade.getEntity(tagDto));
				}
			}
			else {
				results.add(tagFacade.getEntity(tagDto));
			}
		}
		return results;
	}
}
