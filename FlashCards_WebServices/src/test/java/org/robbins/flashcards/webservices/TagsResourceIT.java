
package org.robbins.flashcards.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.client.GenericRestCrudFacade;
import org.robbins.flashcards.client.TagClient;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.tests.webservices.GenericEntityRestTest;
import org.robbins.flashcards.util.TestDtoGenerator;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

@Category(IntegrationTest.class)
@ContextConfiguration(locations = {"classpath*:applicatonContext-client.xml"})
public class TagsResourceIT extends GenericEntityRestTest<TagDto> {

    final static String TAG_NAME = "Web API Test 'Tag'";
    // this entity will be created in @Before and we'll use it for our JUnit tests and
    // then delete it in @After
    private TagDto entity = TestDtoGenerator.createTagDto(TAG_NAME);

    @Inject
    private TagClient client;

    @Override
    public GenericRestCrudFacade<TagDto> getClient() {
        return client;
    }

    @Override
    public void setEntity(TagDto entity) {
        this.entity = entity;
    }

    @Override
    public TagDto getEntity() {
        return this.entity;
    }

    /**
     * Test search by facility in.
     */
    @Test
    public void testSearchByName() throws FlashcardsException
	{
        final TagDto searchResult = client.findByName(TAG_NAME);

        // test that our get worked
        assertTrue(searchResult != null);
        assertEquals(searchResult.getName(), TAG_NAME);
    }

    /**
     * Execute updateEntity.
     */
    @Test
    public void testUpdateEntity() throws FlashcardsException {
        final Long id = getEntity().getId();
        final String UPDATED_VALUE = "updated value";

        final TagDto entity = new TagDto(id);
        entity.setName(UPDATED_VALUE);

        client.update(entity);

        // double-check the Entity info was updated by re-pulling the Entity
        final TagDto retrievedEntity = client.findOne(id);
        assertEquals(UPDATED_VALUE, retrievedEntity.getName());
    }
}
