
package org.robbins.flashcards.webservices;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.client.FlashcardClient;
import org.robbins.flashcards.client.GenericRestCrudFacade;
import org.robbins.flashcards.client.TagClient;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.FlashCardDtoBuilder;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.TagDtoBuilder;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.tests.webservices.GenericEntityRestTest;
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
public class FlashCardsResourceIT extends GenericEntityRestTest<FlashCardDto, String> {

    // this entity will be created in @Before and we'll use it for our JUnit tests and
    // then delete it in @After
    private FlashCardDto entity = new FlashCardDtoBuilder().withQuestion("Web API Test 'Question'").withAnswer("Web API Test 'Answer'").build();

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

    @Inject
    private TagClient tagClient;

    @Override
    public GenericRestCrudFacade<FlashCardDto, String> getClient() {
        return client;
    }

    @Test
    public void testSearchByTagsIn() throws FlashcardsException
	{
        Set<TagDto> tags = new HashSet<>();
        tags.add(new TagDto("2", "two"));
        tags.add(new TagDto("20", "twenty"));

        List<FlashCardDto> searchResult = client.findByTagsIn(tags);

        assertTrue(searchResult.size() > 0);
    }

    @Test
    public void testUpdateEntity() throws FlashcardsException {
        final String id = getEntity().getId();
        final String UPDATED_VALUE = "updated value";

        final FlashCardDto entity = new FlashCardDto(id);
        entity.setQuestion(UPDATED_VALUE);

        client.update(entity);

        // double-check the Entity info was updated by re-pulling the Entity
        final FlashCardDto retrievedEntity = client.findOne(id);
        assertEquals(UPDATED_VALUE, retrievedEntity.getQuestion());
    }

    @Test
    public void createNewFlashCard_WithNewTag() throws FlashcardsException {

        FlashCardDto flashCard = new FlashCardDto();
        flashCard.setQuestion("Question4");
        flashCard.setAnswer("Answer4");

        TagDto tag = new TagDto();
        tag.setName("tag4");
        flashCard.getTags().add(tag);

        FlashCardDto newFlashCard = client.save(flashCard);

        assertThat(newFlashCard.getId().length(), greaterThan(0));
        assertThat(newFlashCard.getTags().size(), greaterThan(0));

        client.delete(newFlashCard.getId());
    }

    @Test
    public void testFindFlashcardsForTag() throws FlashcardsException {
        FlashCardDto flashCard = setupFlashcard();

        List<FlashCardDto> results = client.findFlashcardsForTag(flashCard.getTags().iterator().next().getId(), null);
        assertTrue(results != null);
        assertTrue(results.size() == 1);

        cleanupFlashcard(flashCard);
    }

    private FlashCardDto setupFlashcard() throws FlashcardsException {
        FlashCardDto flashCardDto = new FlashCardDtoBuilder().withQuestion("question").withAnswer("answer").build();
        flashCardDto.setTags(Sets.newHashSet(new TagDtoBuilder().withName("tag_name").build()));
        return client.save(flashCardDto);
    }

    private void cleanupFlashcard(FlashCardDto flashCard) {
        client.delete(flashCard.getId());
        tagClient.delete(flashCard.getTags().iterator().next().getId());
    }
}
