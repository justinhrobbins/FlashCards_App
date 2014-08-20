
package org.robbins.flashcards.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.tests.webservices.GenericEntityRestTest;
import org.robbins.flashcards.util.TestEntityGenerator;
import org.robbins.flashcards.webservices.util.ResourceUrls;
import org.robbins.tests.IntegrationTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

@Category(IntegrationTest.class)
@ContextConfiguration(locations = { "classpath*:applicatonContext-webServices-test.xml" })
public class TagsResourceIT extends GenericEntityRestTest<TagDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagsResourceIT.class);

    // this entity will be created in @Before and we'll use it for our JUnit tests and
    // then delete it in @After
    private TagDto entity = TestEntityGenerator.createTagDto("Web API Test 'Tag'");

    @Override
    public void setEntity(final TagDto entity) {
        this.entity = entity;
    }

    @Override
    public TagDto getEntity() {
        return entity;
    }

    @Override
    public String getEntityListUrl() {
        return getServerAddress() + ResourceUrls.tags;
    }

    @Override
    public String getEntityUrl() {
        return getServerAddress() + ResourceUrls.tag;
    }

    @Override
    public String postEntityUrl() {
        return getServerAddress() + ResourceUrls.tags;
    }

    @Override
    public String putEntityUrl() {
        return getServerAddress() + ResourceUrls.tag;
    }

    @Override
    public String deleteEntityUrl() {
        return getServerAddress() + ResourceUrls.tag;
    }

    @Override
    public String searchUrl() {
        return getServerAddress() + ResourceUrls.tagsSearch;
    }

    @Override
    public Class<TagDto> getClazz() {
        return TagDto.class;
    }

    @Override
    public Class<TagDto[]> getClazzArray() {
        return TagDto[].class;
    }

    /**
     * Test search by facility in.
     */
    @Test
    public void testSearchByName() {
        Map<String, String> uriVariables = new HashMap<String, String>();

        String name = "Web API Test 'Tag'";
        uriVariables.put("name", name);

        // search results
        TagDto searchResult = searchSingleEntity(searchUrl(), uriVariables, TagDto.class);

        // test that our get worked
        assertTrue(searchResult != null);
    }

    /**
     * Execute updateEntity.
     */
    @Test
    public void testUpdateEntity() {
        Long id = getEntity().getId();
        String updatedValue = "updated value";

        TagDto entity = new TagDto();
        entity.setName(updatedValue);

        // build the URL
        String apiUrl = getServerAddress() + ResourceUrls.tagUpdate;

        // set the URL parameter
        Map<String, String> vars = Collections.singletonMap("id", String.valueOf(id));

        // make the REST call
        HttpStatus status = updateEntity(apiUrl, vars, entity);
        assertEquals(status.toString(), "204");

        // double-check the Entity info was updated by re-pulling the Entity
        TagDto retrievedEntity = getEntity(getEntityUrl(), getEntity().getId(),
                getClazz());
        assertEquals(updatedValue, retrievedEntity.getName());
    }
}
