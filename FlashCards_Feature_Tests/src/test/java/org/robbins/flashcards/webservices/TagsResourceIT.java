
package org.robbins.flashcards.webservices;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.client.FlashcardClient;
import org.robbins.flashcards.client.GenericRestCrudFacade;
import org.robbins.flashcards.client.TagClient;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.builder.FlashCardDtoBuilder;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.builder.TagDtoBuilder;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.tests.webservices.GenericEntityRestTest;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(IntegrationTest.class)
@ContextConfiguration(locations = {"classpath*:applicatonContext-client.xml"})
public class TagsResourceIT extends GenericEntityRestTest<TagDto, Long> {

    final static String TAG_NAME = "Web API Test 'Tag'";
    // this entity will be created in @Before and we'll use it for our JUnit tests and
    // then delete it in @After
    private TagDto entity = new TagDtoBuilder().withName(TAG_NAME).build();

    @Inject
    private TagClient client;

    @Inject
    private FlashcardClient flashcardClient;

    @Override
    public GenericRestCrudFacade<TagDto, Long> getClient() {
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

    @Test
    public void testSearchByName() throws FlashcardsException
	{
        final TagDto searchResult = client.findByName(TAG_NAME);

        // test that our get worked
        assertTrue(searchResult != null);
        assertEquals(searchResult.getName(), TAG_NAME);
    }

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

    @Test
    public void testFindByCreatedBy() throws FlashcardsException {
        final Long userId = RandomUtils.nextLong(0L, Long.MAX_VALUE);
        final List<TagDto> results = client.findByCreatedBy(userId, null);

        assertTrue(results != null);
        assertTrue(results.size() > 0);
    }

    @Test
    public void testFindTagsForFlashcard() throws FlashcardsException {
        final FlashCardDto flashCard = setupFlashcard();

        final List<TagDto> results = client.findTagsForFlashcard(flashCard.getId(), null);
        assertTrue(results != null);
        assertTrue(results.size() == 1);

        cleanupFlashcard(flashCard);
    }

    private FlashCardDto setupFlashcard() throws FlashcardsException {
        final FlashCardDto flashCardDto = new FlashCardDtoBuilder().withQuestion("question").withAnswer("answer").build();
        flashCardDto.setTags(Sets.newHashSet(new TagDtoBuilder().withName("tag_name").build()));
        return flashcardClient.save(flashCardDto);
    }

    private void cleanupFlashcard(FlashCardDto flashCard) {
        flashcardClient.delete(flashCard.getId());
        client.delete(flashCard.getTags().iterator().next().getId());
    }
}
