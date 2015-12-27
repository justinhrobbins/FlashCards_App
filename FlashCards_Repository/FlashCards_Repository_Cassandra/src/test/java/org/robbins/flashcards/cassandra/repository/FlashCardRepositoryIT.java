package org.robbins.flashcards.cassandra.repository;


import com.google.common.collect.Lists;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.domain.*;
import org.robbins.flashcards.repository.FlashCardRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class FlashCardRepositoryIT extends AbstractCassandraIntegrationTest {

    private final Long ID = 1L;
    private final String QUESTION = "question1";
    private final String ANSWER = "answer1";

    @Inject
    private FlashCardRepository<FlashCardCassandraEntity, TagCassandraEntity, Long> flashCardRepository;

    @Inject
    private TagFlashcardCassandraRepository tagFlashcardCassandraRepository;

    @Test
    public void testFindOne() {
        final FlashCardCassandraEntity fc = new FlashCardCassandraBuilder()
                .withId(ID).build();

        final FlashCardCassandraEntity result = flashCardRepository.findOne(fc.getId());
        assertThat(result.getQuestion(), is(QUESTION));
        assertThat(result.getAnswer(), is(ANSWER));
    }

    @Test
    public void testFindAll() {
        final List<FlashCardCassandraEntity> flashcards = Lists.newArrayList(flashCardRepository.findAll());
        assertThat(flashcards.size(), greaterThan(0));
    }

    @Test
    public void testSave() {
        final FlashCardCassandraEntity flashcard = new FlashCardCassandraBuilder()
                .withId(RandomUtils.nextLong())
                .withQuestion("new question")
                .withAnswer("new answer")
                .build();

        FlashCardCassandraEntity result = flashCardRepository.save(flashcard);
        assertThat(result.getId(), is(flashcard.getId()));
        assertThat(result.getQuestion(), is(flashcard.getQuestion()));
        assertThat(result.getAnswer(), is(flashcard.getAnswer()));
    }

    @Test
    public void testSave_WithTags() {
        final TagCassandraEntity tag1 = testUtils.createTagEntity();
        final TagCassandraEntity tag2 = testUtils.createTagEntity();
        final FlashCardCassandraEntity flashcard = new FlashCardCassandraBuilder()
                .withId(RandomUtils.nextLong())
                .withQuestion("new question")
                .withAnswer("new answer")
                .withTag(tag1)
                .withTag(tag2)
                .build();

        FlashCardCassandraEntity result = flashCardRepository.save(flashcard);
        assertThat(result.getId(), is(flashcard.getId()));
        assertThat(result.getTags().size(), is(2));

        List<TagFlashCardCassandraEntity> tagFlashcards = tagFlashcardCassandraRepository.findByTagId(tag1.getId());
        assertThat(tagFlashcards.size(), is(1));
    }

    @Test
    public void testDelete() {
        FlashCardCassandraEntity toDelete = testUtils.createFlashCardEntity();

        flashCardRepository.delete(toDelete.getId());
    }
}
