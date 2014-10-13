
package org.robbins.flashcards.repository.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Transactional
@Component
public class DefaultFlashcardRepositoryFacade extends
		AbstractCrudRepositoryFacadeImpl<FlashCardDto, FlashCard> implements FlashcardFacade {

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
        FlashCard resultEntity = getRepository().save(entity);
        FlashCardDto resultDto = getConverter().getDto(resultEntity);
        return resultDto;
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tagDtos)
            throws RepositoryException {
        return getConverter().getDtos(getRepository().findByTagsIn(getTags(tagDtos)), null);
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tagDtos,
            final PageRequest page) throws RepositoryException {
        return getConverter().getDtos(getRepository().findByTagsIn(getTags(tagDtos), page), null);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question)
            throws RepositoryException {
        return getConverter().getDtos(getRepository().findByQuestionLike(question), null);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question,
            final PageRequest page)
            throws RepositoryException {
        return getConverter().getDtos(getRepository().findByQuestionLike(question, page), null);
    }

    @Override
    public FlashCardDto findByQuestion(final String question) throws RepositoryException {
        return getConverter().getDto(getRepository().findByQuestion(question));
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
            if (tagDto.getId() == null || tagDto.getId() == 0) {
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
