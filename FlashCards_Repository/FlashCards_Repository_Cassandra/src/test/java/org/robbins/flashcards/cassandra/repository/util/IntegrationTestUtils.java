package org.robbins.flashcards.cassandra.repository.util;


import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.robbins.flashcards.cassandra.repository.FlashCardCassandraRepository;
import org.robbins.flashcards.cassandra.repository.TagCassandraRepository;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.builder.FlashCardDtoBuilder;
import org.robbins.flashcards.dto.builder.TagDtoBuilder;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.FlashCardFacade;
import org.robbins.flashcards.facade.TagFacade;
import org.springframework.stereotype.Component;

@Component
public class IntegrationTestUtils {

    @Inject
    private FlashCardCassandraRepository flashCardRepository;

    @Inject
    private TagCassandraRepository tagRepository;

    @Inject
    private FlashCardFacade flashCardFacade;

    @Inject
    private TagFacade tagFacade;

    public final FlashCardCassandraEntity createFlashCardEntity() {
        final FlashCardCassandraEntity flashCard = new FlashCardCassandraBuilder()
                .withId(RandomUtils.nextLong(0L, Long.MAX_VALUE))
                .withQuestion(RandomStringUtils.randomAlphabetic(10))
                .withAnswer(RandomStringUtils.randomAlphabetic(10))
                .build();

        return flashCardRepository.save(flashCard);
    }

    public final TagCassandraEntity createTagEntity() {
        final TagCassandraEntity tag = new TagCassandraBuilder()
                .withId(RandomUtils.nextLong(0L, Long.MAX_VALUE))
                .withName(RandomStringUtils.randomAlphabetic(10)).build();

        return tagRepository.save(tag);
    }

    public final FlashCardDto createFlashCardDto() throws FlashCardsException
    {
        final FlashCardDto dto = new FlashCardDtoBuilder()
                .withQuestion(RandomStringUtils.randomAlphabetic(10))
                .withAnswer(RandomStringUtils.randomAlphabetic(10))
                .build();

        return flashCardFacade.save(dto);
    }

    public final TagDto createTagDto() throws FlashCardsException
    {
        final TagDto tag = new TagDtoBuilder()
                .withName(RandomStringUtils.randomAlphabetic(10)).build();

        return tagFacade.save(tag);
    }
}
