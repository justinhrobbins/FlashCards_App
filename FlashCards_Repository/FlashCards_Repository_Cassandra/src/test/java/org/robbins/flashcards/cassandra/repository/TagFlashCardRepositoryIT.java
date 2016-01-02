package org.robbins.flashcards.cassandra.repository;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.domain.TagFlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagFlashCardKey;

import com.google.common.collect.Lists;

public class TagFlashCardRepositoryIT extends AbstractCassandraIntegrationTest {

    private static final Long TAG_ID = 1L;
    private static final Long FLASHCARD_ID = 1L;

    @Inject
    private TagFlashCardCassandraRepository repository;

    @Test
    public void testFindOne() {
        final TagFlashCardKey key = new TagFlashCardKey(TAG_ID, FLASHCARD_ID);

        final TagFlashCardCassandraEntity result = repository.findOne(key);
        assertThat(result.getId(), is(notNullValue()));
        assertThat(result.getId().getTagId(), is(TAG_ID));
        assertThat(result.getId().getFlashCardId(), is(FLASHCARD_ID));
    }

    @Test
    public void testFindByTagId() {
        final List<TagFlashCardCassandraEntity> results = repository.findByTagId(TAG_ID);
        assertThat(results, is(notNullValue()));
        assertThat(results.get(0).getId(), is(notNullValue()));
        assertThat(results.get(0).getId().getTagId(), is(TAG_ID));
        assertThat(results.get(0).getId().getFlashCardId(), is(FLASHCARD_ID));
    }

    @Test
    public void testFindAll() {
        final List<TagFlashCardCassandraEntity> tagFlashCards = Lists.newArrayList(repository.findAll());
        assertThat(tagFlashCards.size(), greaterThan(0));
        assertThat(tagFlashCards.get(0).getId(), is(notNullValue()));
        assertThat(tagFlashCards.get(0).getId().getTagId(), is(notNullValue()));
        assertThat(tagFlashCards.get(0).getId().getFlashCardId(), is(notNullValue()));
    }
}
