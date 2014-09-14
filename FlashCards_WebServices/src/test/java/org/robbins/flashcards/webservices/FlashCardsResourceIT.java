
package org.robbins.flashcards.webservices;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.client.DefaultFlashcardClient;
import org.robbins.flashcards.client.FlashcardClient;
import org.robbins.flashcards.client.GenericRestCrudFacade;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.tests.webservices.GenericEntityRestTest;
import org.robbins.flashcards.util.TestDtoGenerator;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@Category(IntegrationTest.class)
@ContextConfiguration(locations = {"classpath*:applicatonContext-client.xml"})
public class FlashCardsResourceIT extends GenericEntityRestTest<FlashCardDto> {

    // this entity will be created in @Before and we'll use it for our JUnit tests and
    // then delete it in @After
    private FlashCardDto entity = TestDtoGenerator.createFlashCardDto(
            "Web API Test 'Question'", "Web API Test 'Answer'");

    @Override
    public void setEntity(final FlashCardDto entity) {
        this.entity = entity;
    }

    @Override
    public FlashCardDto getEntity() {
        return entity;
    }

    @Inject
    private FlashcardClient client;

    @Override
    public GenericRestCrudFacade<FlashCardDto> getClient() {
        return client;
    }

    /**
     * Test search by facility in.
     */
    @Test
    public void testSearchByTagsIn() throws ServiceException {
        Set<TagDto> tags = new HashSet<>();
        tags.add(new TagDto(2L));
        tags.add(new TagDto(20L));

        // search result
        List<FlashCardDto> searchResult = client.findByTagsIn(tags);

        // test that our get worked
        assertTrue(searchResult.size() > 0);
    }

    /**
     * Execute updateEntity.
     */
    @Test
    public void testUpdateEntity() throws ServiceException {
        final Long id = getEntity().getId();
        final String UPDATED_VALUE = "updated value";

        final FlashCardDto entity = new FlashCardDto(id);
        entity.setQuestion(UPDATED_VALUE);

        client.update(entity);

        // double-check the Entity info was updated by re-pulling the Entity
        final FlashCardDto retrievedEntity = client.findOne(id);
        assertEquals(UPDATED_VALUE, retrievedEntity.getQuestion());
    }

    @Test
    public void createNewFlashCard_WithNewTag() throws ServiceException {

        FlashCardDto flashCard = new FlashCardDto();
        flashCard.setQuestion("Question4");
        flashCard.setAnswer("Answer4");

        TagDto tag = new TagDto();
        tag.setName("tag4");

        flashCard.getTags().add(tag);

        // make the REST call
        FlashCardDto newFlashCard = client.save(flashCard);

        assertThat(newFlashCard.getId(), greaterThan(0L));
        assertThat(newFlashCard.getTags().size(), greaterThan(0));

        client.delete(newFlashCard.getId());
    }
}
