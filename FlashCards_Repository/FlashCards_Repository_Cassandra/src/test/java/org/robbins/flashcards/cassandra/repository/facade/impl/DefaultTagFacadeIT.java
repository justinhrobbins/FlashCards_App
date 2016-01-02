
package org.robbins.flashcards.cassandra.repository.facade.impl;

import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.AbstractCassandraIntegrationTest;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.builder.TagDtoBuilder;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.TagFacade;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
public class DefaultTagFacadeIT extends AbstractCassandraIntegrationTest {

    private final Long TAG_ID = 1L;
    private final Long NON_EXISTING_TAG_ID = 999L;
    private final String TAG_NAME = "tag1";

    @Inject
    private TagFacade tagFacade;

    @Test
    public void testList() throws FlashCardsException
	{
        final List<TagDto> results = tagFacade.list();

        assertThat(results, is(notNullValue()));
        assertThat(results.size(), greaterThan(0));
    }

    @Test
    public void testFindOne() throws FlashCardsException
    {

        final TagDto result = tagFacade.findOne(TAG_ID);
        assertThat(result.getId(), is(TAG_ID));
    }

    @Test
    public void testFindOne_ShouldReturnNull_WhenIdDoesNotExist() throws FlashCardsException
    {

        final TagDto result = tagFacade.findOne(NON_EXISTING_TAG_ID);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void testFindByName() throws FlashCardsException
    {

        final TagDto result = tagFacade.findByName(TAG_NAME);
        assertThat(result.getName(), is(TAG_NAME));
    }

    @Test
    public void testFindByName_ShouldReturnNull_WhenNameDoesNotExist() throws FlashCardsException
    {

        final TagDto result = tagFacade.findByName("tag does not exist");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void testSave() throws FlashCardsException
    {
        final String TAG_NAME = "new name";
        final TagDto tag = new TagDtoBuilder()
                .withName(TAG_NAME).build();
        final TagDto result =  tagFacade.save(tag);

        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is(TAG_NAME));
    }

    @Test
    public void testUpdate() throws FlashCardsException
    {
        final String UPDATED_NAME = "updated name";
        final TagDto tagToUpdate = testUtils.createTagDto();
        tagToUpdate.setName(UPDATED_NAME);

        final TagDto result =  tagFacade.save(tagToUpdate);

        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is(UPDATED_NAME));
    }

    @Test
    public void testDelete() throws FlashCardsException
    {
        final TagDto tagToDelete = testUtils.createTagDto();

        tagFacade.delete(tagToDelete.getId());
    }
}
