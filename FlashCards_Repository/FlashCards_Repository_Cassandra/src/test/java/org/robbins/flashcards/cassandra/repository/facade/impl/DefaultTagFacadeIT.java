
package org.robbins.flashcards.cassandra.repository.facade.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.cassandraunit.spring.CassandraDataSet;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.AbstractCassandraIntegrationTest;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.TagDtoBuilder;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
@CassandraDataSet(value = {"cql/TagRepositoryIT.cql"}, keyspace = "flashcardsapp")
public class DefaultTagFacadeIT extends AbstractCassandraIntegrationTest {

    private final String TAG_ID = "eaa488a0-b0d8-11e4-af90-12e3f512a338";
    private final String TAG_NAME = "tag1";

    @Inject
    private TagFacade tagFacade;

    @Test
    public void testList() throws FlashcardsException
	{
        final List<TagDto> results = tagFacade.list();

        assertThat(results, is(notNullValue()));
        assertThat(results.size(), greaterThan(0));
    }

    @Test
    public void testFindOne() throws FlashcardsException {

        final TagDto result = tagFacade.findOne(TAG_ID);
        assertThat(result.getId(), is(TAG_ID));
    }

    @Test
    public void testFindOne_ShouldReturnNull_WhenIdDoesNotExist() throws FlashcardsException {

        final TagDto result = tagFacade.findOne(UUID.randomUUID().toString());
        assertThat(result, is(nullValue()));
    }

    @Test
    public void testFindByName() throws FlashcardsException {

        final TagDto result = tagFacade.findByName(TAG_NAME);
        assertThat(result.getName(), is(TAG_NAME));
    }

    @Test
    public void testFindByName_ShouldReturnNull_WhenNameDoesNotExist() throws FlashcardsException {

        final TagDto result = tagFacade.findByName("tag does not exist");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void testSave() throws FlashcardsException
    {
        final String TAG_NAME = "new name";
        final TagDto tag = new TagDtoBuilder()
                .withName(TAG_NAME).build();
        final TagDto result =  tagFacade.save(tag);

        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is(TAG_NAME));
    }

    @Test
    public void testUpdate() throws FlashcardsException
    {
        final String UPDATED_NAME = "updated name";
        final TagDto tagToUpdate = createTag();
        tagToUpdate.setName(UPDATED_NAME);

        final TagDto result =  tagFacade.save(tagToUpdate);

        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is(UPDATED_NAME));
    }

    @Test
    public void testDelete() throws FlashcardsException {
        final TagDto tagToDelete = createTag();

        tagFacade.delete(tagToDelete.getId());
    }

    private TagDto createTag() throws FlashcardsException {
        final TagDto tag = new TagDtoBuilder()
                .withName(RandomStringUtils.random(10)).build();

        return tagFacade.save(tag);
    }
}
