
package org.robbins.flashcards.cassandra.repository.facade;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component("flashcardRepositoryFacade")
public class DefaultFlashcardFacade implements
        FlashcardFacade {

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tags) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tags, final PageRequest page) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question, final PageRequest page) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public FlashCardDto findByQuestion(final String question) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> findFlashcardsForTag(final String tagId, final Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> list() throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> list(final Integer page, final Integer size, final String sort, final String direction) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> list(final Integer page, final Integer size, final String sort, final String direction, final Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public Long count() {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public FlashCardDto findOne(final String s) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public FlashCardDto findOne(final String s, final Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public FlashCardDto save(final FlashCardDto entity) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public void delete(String s) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardDto> findByCreatedBy(final String userId, final Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }
}
