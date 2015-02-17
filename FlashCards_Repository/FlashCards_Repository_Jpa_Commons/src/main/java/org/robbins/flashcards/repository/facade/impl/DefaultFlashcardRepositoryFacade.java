
package org.robbins.flashcards.repository.facade.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.conversion.DtoConverter;
import org.robbins.flashcards.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Component("flashcardRepositoryFacade")
public class DefaultFlashcardRepositoryFacade extends
		AbstractCrudRepositoryFacadeImpl<FlashCardDto, FlashCard, String> implements FlashcardFacade {

	@Inject
	private FlashCardRepository repository;

    @Inject
    private TagRepository tagRepository;

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
	public FlashCardRepository getRepository() {
		return repository;
	}

    @Override
    public FlashCardDto save(final FlashCardDto dto) throws RepositoryException {
        FlashCard entity = getConverter().getEntity(dto);
        entity.setTags(configureTags(dto.getTags()));
        FlashCard result = getRepository().save(entity);
        return convertAndInitializeEntity(result);
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tagDtos)
            throws RepositoryException {
        List<FlashCard> results = getRepository().findByTagsIn(getTags(tagDtos));
        return convertAndInitializeEntities(results);
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tagDtos,
            final PageRequest page) throws RepositoryException {
        List<FlashCard> results =  getRepository().findByTagsIn(getTags(tagDtos), page);
        return convertAndInitializeEntities(results);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question)
            throws RepositoryException {
        List<FlashCard> results = getRepository().findByQuestionLike(question);
        return convertAndInitializeEntities(results);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question,
            final PageRequest page)
            throws RepositoryException {
        List<FlashCard> results =  getRepository().findByQuestionLike(question, page);
        return convertAndInitializeEntities(results);
    }

    @Override
    public FlashCardDto findByQuestion(final String question) throws RepositoryException {
        FlashCard result = getRepository().findByQuestion(question);
        return convertAndInitializeEntity(result);
    }

    @Override
    public List<FlashCardDto> findFlashcardsForTag(String tagId, Set<String> fields) throws FlashcardsException {
        List<FlashCard> results = getRepository().findByTags_Id(tagId);
        return convertAndInitializeEntities(results, fields);
    }

    private Set<Tag> getTags(final Set<TagDto> tagDtos) {
        List<TagDto> tagDtoList = Lists.newArrayList(tagDtos);
        List<Tag> tagList = tagConverter.getEtnties(tagDtoList);
        return Sets.newHashSet(tagList);
    }

    private Set<Tag> configureTags(final Set<TagDto> tags) {

        Set<Tag> results = new HashSet<>();
        for (TagDto tagDto : tags) {
            Tag tag;
            // if we don't have the id of the Tag
            if (tagDto.getId() == null || StringUtils.isEmpty(tagDto.getId())) {
                // try to get the existing Tag
                tag = tagRepository.findByName(tagDto.getName());

                // does the Tag exist?
                if (tag == null) {
                    // tag doesn't exist, re-add the Tag as-as
                    tag = tagConverter.getEntity(tagDto);
                }
            } else {
                tag = tagConverter.getEntity(tagDto);
            }
            results.add(tag);
        }
        return results;
    }
}
