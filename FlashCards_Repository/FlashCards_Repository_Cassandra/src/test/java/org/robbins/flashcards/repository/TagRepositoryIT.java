package org.robbins.flashcards.repository;


import com.google.common.collect.Lists;
import org.cassandraunit.spring.CassandraDataSet;
import org.junit.Test;
import org.robbins.flashcards.repository.domain.TagCassandra;
import org.robbins.flashcards.repository.domain.TagCassandraBuilder;
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
        TagCassandra tag = new TagCassandraBuilder()
                .withId(UUID.fromString("eaa488a0-b0d8-11e4-af90-12e3f512a338")).build();

        TagCassandra result = tagRepository.findOne(tag.getId());
        assertThat(result.getName(), is("tag1"));
    }

    @Test
    public void testFindAll() {
        List<TagCassandra> tags = Lists.newArrayList(tagRepository.findAll());
        assertThat(tags.size(), greaterThan(0));
    }

    @Test
    public void insertTag() {
        TagCassandra tag = new TagCassandraBuilder().withId(UUID.randomUUID()).withName("new name").build();

        TagCassandra result = tagRepository.save(tag);
        assertThat(result.getId(), is(tag.getId()));
        assertThat(result.getName(), is(tag.getName()));
    }
}
