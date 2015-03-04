package org.robbins.flashcards.cassandra.repository;


import com.google.common.collect.Lists;
import org.apache.commons.lang.RandomStringUtils;
import org.cassandraunit.spring.CassandraDataSet;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@CassandraDataSet(value = {"cql/TagRepositoryIT.cql"}, keyspace = "flashcardsapp")
public class TagRepositoryIT extends AbstractCassandraIntegrationTest {

    private final String ID = "eaa488a0-b0d8-11e4-af90-12e3f512a338";
    private final String TAG_NAME = "tag1";

    @Autowired
    TagCassandraRepository tagRepository;

    @Test
    public void testFindOne() {
        final TagCassandraDto tag = new TagCassandraBuilder()
                .withId(UUID.fromString(ID)).build();

        final TagCassandraDto result = tagRepository.findOne(tag.getId());
        assertThat(result.getName(), is(TAG_NAME));
    }

    @Test
    public void testFindByName() {

        final TagCassandraDto result = tagRepository.findByName(TAG_NAME);
        assertThat(result.getName(), is(TAG_NAME));
    }

    @Test
    public void testFindAll() {
        final List<TagCassandraDto> tags = Lists.newArrayList(tagRepository.findAll());
        assertThat(tags.size(), greaterThan(0));
    }

    @Test
    public void testSave() {
        final TagCassandraDto tag = new TagCassandraBuilder().withId(UUID.randomUUID()).withName("new name").build();

        TagCassandraDto result = tagRepository.save(tag);
        assertThat(result.getId(), is(tag.getId()));
        assertThat(result.getName(), is(tag.getName()));
    }

    @Test
    public void testDelete() {
        TagCassandraDto toDelete = createTag();

        tagRepository.delete(toDelete.getId());
    }

    private TagCassandraDto createTag() {
        final TagCassandraDto tag = new TagCassandraBuilder()
                .withId(UUID.randomUUID())
                .withName(RandomStringUtils.random(10)).build();

        return tagRepository.save(tag);
    }
}
