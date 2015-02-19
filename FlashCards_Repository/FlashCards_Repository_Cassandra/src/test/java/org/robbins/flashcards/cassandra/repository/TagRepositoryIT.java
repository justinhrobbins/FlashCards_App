package org.robbins.flashcards.cassandra.repository;


import com.google.common.collect.Lists;
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

    @Autowired
    TagRepository tagRepository;

    @Test
    public void testFindOne() {
        TagCassandraDto tag = new TagCassandraBuilder()
                .withId(UUID.fromString("eaa488a0-b0d8-11e4-af90-12e3f512a338")).build();

        TagCassandraDto result = tagRepository.findOne(tag.getId());
        assertThat(result.getName(), is("tag1"));
    }

    @Test
    public void testFindAll() {
        List<TagCassandraDto> tags = Lists.newArrayList(tagRepository.findAll());
        assertThat(tags.size(), greaterThan(0));
    }

    @Test
    public void insertTag() {
        TagCassandraDto tag = new TagCassandraBuilder().withId(UUID.randomUUID()).withName("new name").build();

        TagCassandraDto result = tagRepository.save(tag);
        assertThat(result.getId(), is(tag.getId()));
        assertThat(result.getName(), is(tag.getName()));
    }
}
