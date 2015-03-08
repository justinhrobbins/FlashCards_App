package org.robbins.flashcards.cassandra.repository;


import com.google.common.collect.Lists;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.robbins.flashcards.repository.TagRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class TagRepositoryIT extends AbstractCassandraIntegrationTest {

    private final UUID TAG_ID = UUID.fromString("eaa488a0-b0d8-11e4-af90-12e3f512a338");
    private final String TAG_NAME = "tag1";
    private final UUID FLASHCARD_ID = UUID.fromString("0791e3ec-c072-11e4-8dfc-aa07a5b093db");

    @Inject
    private TagRepository<TagCassandraEntity, UUID> tagRepository;

    @Inject
    private FlashCardRepository<FlashCardCassandraEntity, TagCassandraEntity, UUID> flashCardRepository;

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
        final TagCassandraEntity tag = new TagCassandraBuilder().withId(UUID.randomUUID()).withName("new name").build();

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

        FlashCardCassandraEntity flashcard = flashCardRepository.findOne(FLASHCARD_ID);
        assertThat(flashcard.getTags().get(TAG_ID), is(UPDATED_NAME));
    }

    @Test
    public void testDelete() {
        TagCassandraEntity toDelete = testUtils.createTagEntity();

        tagRepository.delete(toDelete.getId());
    }
}
