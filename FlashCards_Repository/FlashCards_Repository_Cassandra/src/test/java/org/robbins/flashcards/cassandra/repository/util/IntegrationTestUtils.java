package org.robbins.flashcards.cassandra.repository.util;

import org.apache.commons.lang.RandomStringUtils;
import org.robbins.flashcards.cassandra.repository.FlashCardCassandraRepository;
import org.robbins.flashcards.cassandra.repository.TagCassandraRepository;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.FlashCardDtoBuilder;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.TagDtoBuilder;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.facade.TagFacade;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.UUID;

@Component
public class IntegrationTestUtils {

    @Inject
    private FlashCardCassandraRepository flashCardRepository;

    @Inject
    private TagCassandraRepository tagRepository;

    @Inject
    private FlashcardFacade flashCardFacade;

    @Inject
    private TagFacade tagFacade;

    public final FlashCardCassandraEntity createFlashCardEntity() {
        final FlashCardCassandraEntity flashcard = new FlashCardCassandraBuilder()
                .withId(UUID.randomUUID())
                .withQuestion(RandomStringUtils.randomAlphabetic(10))
                .withAnswer(RandomStringUtils.randomAlphabetic(10))
                .build();

        return flashCardRepository.save(flashcard);
    }

    public final TagCassandraEntity createTagEntity() {
        final TagCassandraEntity tag = new TagCassandraBuilder()
                .withId(UUID.randomUUID())
                .withName(RandomStringUtils.randomAlphabetic(10)).build();

        return tagRepository.save(tag);
    }

    public final FlashCardDto createFlashCardDto() throws FlashcardsException {
        final FlashCardDto dto = new FlashCardDtoBuilder()
                .withQuestion(RandomStringUtils.randomAlphabetic(10))
                .withAnswer(RandomStringUtils.randomAlphabetic(10))
                .build();

        return flashCardFacade.save(dto);
    }

    public final TagDto createTagDto() throws FlashcardsException {
        final TagDto tag = new TagDtoBuilder()
                .withName(RandomStringUtils.randomAlphabetic(10)).build();

        return tagFacade.save(tag);
    }
}
