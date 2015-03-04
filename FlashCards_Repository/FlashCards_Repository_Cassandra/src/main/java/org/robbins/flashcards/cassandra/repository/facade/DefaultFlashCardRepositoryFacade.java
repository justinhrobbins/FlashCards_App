
package org.robbins.flashcards.cassandra.repository.facade;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraDto;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraDto;
import org.robbins.flashcards.cassandra.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component("flashcardRepositoryFacade")
public class DefaultFlashCardRepositoryFacade extends AbstractCrudRepositoryFacadeImpl<FlashCardDto, FlashCardCassandraDto> implements
        FlashcardFacade {

    @Inject
	private FlashCardRepository<FlashCardCassandraDto, TagCassandraDto, UUID> repository;

    @Inject
    @Qualifier("flashcardDtoConverter")
    private DtoConverter<FlashCardDto, FlashCardCassandraDto> converter;

    public DtoConverter<FlashCardDto, FlashCardCassandraDto> getConverter()
    {
        return converter;
    }

	public FlashCardRepository<FlashCardCassandraDto, TagCassandraDto, UUID> getRepository() {
		return repository;
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
