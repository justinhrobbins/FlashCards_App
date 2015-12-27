
package org.robbins.flashcards.repository.facade.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.model.util.AuditingUtil;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Component("flashcardRepositoryFacade")
public class DefaultFlashcardRepositoryFacade extends
		AbstractCrudRepositoryFacadeImpl<FlashCardDto, FlashCard, Long> implements FlashcardFacade {

	@Inject
	private FlashCardRepository<FlashCard, Tag, Long> repository;

    @Inject
    private TagRepository<Tag, Long> tagRepository;

    @Inject
    @Qualifier("flashcardDtoConverter")
    private DtoConverter<FlashCardDto, FlashCard> flashcardConverter;

    @Inject
    @Qualifier("tagDtoConverter")
    private DtoConverter<TagDto, Tag> tagConverter;

    @Override
    public DtoConverter<FlashCardDto, FlashCard> getConverter()
    {
        return flashcardConverter;
    }

	@Override
	public FlashCardRepository<FlashCard, Tag, Long> getRepository() {
		return repository;
	}

    @Override
    public FlashCardDto save(final FlashCardDto dto) throws RepositoryException {
        FlashCard entity = getConverter().getEntity(dto);
        AuditingUtil.configureCreatedByAndTime(entity, getAuditingUserId());
        entity.setTags(configureTags(dto.getTags()));
        FlashCard result = repository.save(entity);
        return convertAndInitializeEntity(result);
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tagDtos)
            throws RepositoryException {
        List<FlashCard> results = repository.findByTagsIn(getTags(tagDtos));
        return convertAndInitializeEntities(results);
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tagDtos,
            final PageRequest page) throws RepositoryException {
        List<FlashCard> results =  repository.findByTagsIn(getTags(tagDtos), page);
        return convertAndInitializeEntities(results);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question)
            throws RepositoryException {
        List<FlashCard> results = repository.findByQuestionLike(question);
        return convertAndInitializeEntities(results);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question,
            final PageRequest page)
            throws RepositoryException {
        List<FlashCard> results =  repository.findByQuestionLike(question, page);
        return convertAndInitializeEntities(results);
    }

    @Override
    public FlashCardDto findByQuestion(final String question) throws RepositoryException {
        FlashCard result = repository.findByQuestion(question);
        return convertAndInitializeEntity(result);
    }

    @Override
    public List<FlashCardDto> findFlashcardsForTag(Long tagId, Set<String> fields) throws FlashcardsException {
        List<FlashCard> results = repository.findByTags_Id(tagId);
        return convertAndInitializeEntities(results, fields);
    }

    private Set<Tag> getTags(final Set<TagDto> tagDtos) {
        List<TagDto> tagDtoList = Lists.newArrayList(tagDtos);
        List<Tag> tagList = tagConverter.getEntities(tagDtoList);
        return Sets.newHashSet(tagList);
    }

    private Set<Tag> configureTags(final Set<TagDto> tags) {
        return tags.stream()
                .map(this::configureTag)
                .collect(Collectors.toSet());
    }

    private Tag configureTag(TagDto tagDto) {
        if (tagDto.getId() == null) {
            final Tag existingTag = tagRepository.findByName(tagDto.getName());
            if (existingTag == null) {
                final Tag newTag = tagConverter.getEntity(tagDto);
                AuditingUtil.configureCreatedByAndTime(newTag, getAuditingUserId());
                return newTag;
            } else {
				return existingTag;
            }
        } else {
            return tagConverter.getEntity(tagDto);
        }
    }
}
