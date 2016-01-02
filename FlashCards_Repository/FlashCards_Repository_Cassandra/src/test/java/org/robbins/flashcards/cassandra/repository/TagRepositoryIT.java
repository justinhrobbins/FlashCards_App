package org.robbins.flashcards.cassandra.repository;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.robbins.flashcards.repository.TagRepository;

import com.google.common.collect.Lists;

public class TagRepositoryIT extends AbstractCassandraIntegrationTest {

    private final Long TAG_ID = 1L;
    private final String TAG_NAME = "tag1";
    private final Long FLASHCARD_ID = 1L;

    @Inject
    private TagRepository<TagCassandraEntity, Long> tagRepository;

    @Inject
    private FlashCardRepository<FlashCardCassandraEntity, TagCassandraEntity, Long> flashCardRepository;

    @Test
    public void testFindOne() {
        final TagCassandraEntity result = tagRepository.findOne(TAG_ID);
        assertThat(result.getName(), is(TAG_NAME));
    }

    @Test
    public void testFindByName() {

        final TagCassandraEntity result = tagRepository.findByName(TAG_NAME);
        assertThat(result.getName(), is(TAG_NAME));
    }

    @Test
    public void testFindAll() {
        final List<TagCassandraEntity> tags = Lists.newArrayList(tagRepository.findAll());
        assertThat(tags.size(), greaterThan(0));
    }

    @Test
    public void testSave() {
        final TagCassandraEntity tag = new TagCassandraBuilder().withId(RandomUtils.nextLong(0L, Long.MAX_VALUE)).withName("new name").build();

        TagCassandraEntity result = tagRepository.save(tag);
        assertThat(result.getId(), is(tag.getId()));
        assertThat(result.getName(), is(tag.getName()));
    }

    @Test
    public void testUpdateAnExistingTag() {
        final String UPDATED_NAME = "updated name";
        final TagCassandraEntity existingEntity = tagRepository.findOne(TAG_ID);
        existingEntity.setName(UPDATED_NAME);

        TagCassandraEntity result = tagRepository.save(existingEntity);
        assertThat(result.getName(), is(UPDATED_NAME));

        FlashCardCassandraEntity flashCard = flashCardRepository.findOne(FLASHCARD_ID);
        assertThat(flashCard.getTags().get(TAG_ID), is(UPDATED_NAME));
    }

    @Test
    public void testDelete() {
        TagCassandraEntity toDelete = testUtils.createTagEntity();

        tagRepository.delete(toDelete.getId());
    }
}
