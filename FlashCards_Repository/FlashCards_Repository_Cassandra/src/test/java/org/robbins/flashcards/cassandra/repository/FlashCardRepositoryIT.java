package org.robbins.flashcards.cassandra.repository;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagFlashCardCassandraEntity;
import org.robbins.flashcards.repository.FlashCardRepository;

import com.google.common.collect.Lists;

public class FlashCardRepositoryIT extends AbstractCassandraIntegrationTest {

    private final Long ID = 1L;
    private final String QUESTION = "question1";
    private final String ANSWER = "answer1";

    @Inject
    private FlashCardRepository<FlashCardCassandraEntity, TagCassandraEntity, Long> flashCardRepository;

    @Inject
    private TagFlashCardCassandraRepository tagFlashCardCassandraRepository;

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
        final List<FlashCardCassandraEntity> flashCards = Lists.newArrayList(flashCardRepository.findAll());
        assertThat(flashCards.size(), greaterThan(0));
    }

    @Test
    public void testSave() {
        final FlashCardCassandraEntity flashCard = new FlashCardCassandraBuilder()
                .withId(RandomUtils.nextLong(0L, Long.MAX_VALUE))
                .withQuestion("new question")
                .withAnswer("new answer")
                .build();

        FlashCardCassandraEntity result = flashCardRepository.save(flashCard);
        assertThat(result.getId(), is(flashCard.getId()));
        assertThat(result.getQuestion(), is(flashCard.getQuestion()));
        assertThat(result.getAnswer(), is(flashCard.getAnswer()));
    }

    @Test
    public void testSave_WithTags() {
        final TagCassandraEntity tag1 = testUtils.createTagEntity();
        final TagCassandraEntity tag2 = testUtils.createTagEntity();
        final FlashCardCassandraEntity flashCard = new FlashCardCassandraBuilder()
                .withId(RandomUtils.nextLong(0, Long.MAX_VALUE))
                .withQuestion("new question")
                .withAnswer("new answer")
                .withTag(tag1)
                .withTag(tag2)
                .build();

        final FlashCardCassandraEntity result = flashCardRepository.save(flashCard);
        assertThat(result.getId(), is(flashCard.getId()));
        assertThat(result.getTags().size(), is(2));

        final List<TagFlashCardCassandraEntity> tagFlashCards = tagFlashCardCassandraRepository.findByTagId(tag1.getId());
        assertThat(tagFlashCards.size(), is(1));
    }

    @Test
    public void testDelete() {
        final FlashCardCassandraEntity toDelete = testUtils.createFlashCardEntity();

        flashCardRepository.delete(toDelete.getId());
    }
}
