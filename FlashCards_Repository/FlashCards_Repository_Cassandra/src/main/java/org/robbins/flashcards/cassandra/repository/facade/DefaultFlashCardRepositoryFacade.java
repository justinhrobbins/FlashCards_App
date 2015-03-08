
package org.robbins.flashcards.cassandra.repository.facade;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.cassandra.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.robbins.flashcards.repository.TagRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;

@Component("flashcardRepositoryFacade")
public class DefaultFlashCardRepositoryFacade extends AbstractCrudRepositoryFacadeImpl<FlashCardDto, FlashCardCassandraEntity> implements
        FlashcardFacade {

    @Inject
	private FlashCardRepository<FlashCardCassandraEntity, TagCassandraEntity, UUID> repository;

    @Inject
    private TagRepository<TagCassandraEntity, UUID> tagRepository;

    @Inject
    @Qualifier("flashcardDtoConverter")
    private DtoConverter<FlashCardDto, FlashCardCassandraEntity> converter;

    @Inject
    @Qualifier("tagDtoConverter")
    private DtoConverter<TagDto, TagCassandraEntity> tagConverter;

    public DtoConverter<FlashCardDto, FlashCardCassandraEntity> getConverter()
    {
        return converter;
    }

	public FlashCardRepository<FlashCardCassandraEntity, TagCassandraEntity, UUID> getRepository() {
		return repository;
	}

    @Override
    public FlashCardDto save(final FlashCardDto dto) throws FlashcardsException {
        FlashCardCassandraEntity entity = getConverter().getEntity(dto);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        entity.setTags(configureTags(dto.getTags()));
        FlashCardCassandraEntity result = getRepository().save(entity);
        return getConverter().getDto(result);
    }

    private Map<UUID, String> configureTags(final Set<TagDto> tags) {

        Map<UUID, String> results = new HashMap<>();
        for (TagDto tagDto : tags) {
            TagCassandraEntity tag;

            if (tagDto.getId() == null || StringUtils.isEmpty(tagDto.getId())) {
                tag = tagRepository.findByName(tagDto.getName());

                if (tag == null) {
                    // tag doesn't exist, create it
                    tagDto.setId(UUID.randomUUID().toString());
                    tag = tagRepository.save(tagConverter.getEntity(tagDto));
                }
            } else {
                tag = tagConverter.getEntity(tagDto);
            }
            results.put(tag.getId(), tag.getName());
        }
        return results;
    }

    @Override
    public List<FlashCardDto> findByTagsIn(Set<TagDto> tags) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> findByTagsIn(Set<TagDto> tags, PageRequest page) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(String question) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(String question, PageRequest page) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public FlashCardDto findByQuestion(String question) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> findFlashcardsForTag(String tagId, Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> findByCreatedBy(String userId, Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }
}
