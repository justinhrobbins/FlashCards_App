package org.robbins.flashcards.cassandra.repository;


import com.google.common.collect.Lists;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagFlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagFlashCardKey;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TagFlashCardRepositoryIT extends AbstractCassandraIntegrationTest {

    private static final UUID TAG_ID = UUID.fromString("eaa488a0-b0d8-11e4-af90-12e3f512a338");
    private static final UUID FLASHCARD_ID = UUID.fromString("0791e3ec-c072-11e4-8dfc-aa07a5b093db");

    @Inject
    private TagFlashcardCassandraRepository repository;

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
        final List<TagFlashCardCassandraEntity> tagFlashcards = Lists.newArrayList(repository.findAll());
        assertThat(tagFlashcards.size(), greaterThan(0));
        assertThat(tagFlashcards.get(0).getId(), is(notNullValue()));
        assertThat(tagFlashcards.get(0).getId().getTagId(), is(notNullValue()));
        assertThat(tagFlashcards.get(0).getId().getFlashCardId(), is(notNullValue()));
    }
}
