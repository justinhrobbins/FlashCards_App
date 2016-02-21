
package org.robbins.flashcards.webservices;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.client.FlashCardClient;
import org.robbins.flashcards.client.GenericRestCrudFacade;
import org.robbins.flashcards.client.TagClient;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.builder.FlashCardDtoBuilder;
import org.robbins.flashcards.dto.builder.TagDtoBuilder;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.tests.webservices.GenericEntityRestTest;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@Category(IntegrationTest.class)
@ContextConfiguration(locations = {"classpath*:META-INF/applicatonContext-client.xml"})
public class FlashCardsResourceIT extends GenericEntityRestTest<FlashCardDto, Long> {

    // this entity will be created in @Before and we'll use it for our JUnit tests and
    // then delete it in @After
    private FlashCardDto entity = new FlashCardDtoBuilder()
            .withQuestion(randomQuestion())
            .withAnswer(randomQuestion())
            .build();

    @Override
    public void setEntity(final FlashCardDto entity) {
        this.entity = entity;
    }

    @Override
    public FlashCardDto getEntity() {
        return entity;
    }

    @Inject
    private FlashCardClient client;

    @Inject
    private TagClient tagClient;

    @Override
    public GenericRestCrudFacade<FlashCardDto, Long> getClient() {
        return client;
    }

    @Test
    public void testSearchByTagsIn() throws FlashCardsException
	{
        final FlashCardDto flashCard = givenFlashCardHasTwoTags();

        final List<FlashCardDto> searchResult = client.findByTagsIn(flashCard.getTags());

        assertTrue(searchResult.size() > 0);

        cleanupFlashCard(flashCard);
    }

    private FlashCardDto givenFlashCardHasTwoTags() {
        final FlashCardDto flashCard = setupFlashCard();
        TagDto tag1 = new TagDtoBuilder().withName(randomTagName()).build();
        TagDto tag2 = new TagDtoBuilder().withName(randomTagName()).build();

        tag1 = tagClient.save(tag1);
        tag2 = tagClient.save(tag2);

        flashCard.getTags().add(tag1);
        flashCard.getTags().add(tag2);

        return client.save(flashCard);
    }

    private FlashCardDto setupFlashCard() throws FlashCardsException
    {
        final FlashCardDto flashCardDto = new FlashCardDtoBuilder()
                .withQuestion(randomQuestion())
                .withAnswer(randomAnwer())
                .build();
        return client.save(flashCardDto);
    }

    @Test
    public void testUpdateEntity() throws FlashCardsException
    {
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
    public void createNewFlashCard_WithNewTag() throws FlashCardsException
    {

        final FlashCardDto flashCard = new FlashCardDto();
        flashCard.setQuestion(randomQuestion());
        flashCard.setAnswer(randomAnwer());

        final TagDto tag = new TagDto();
        tag.setName(randomTagName());
        flashCard.getTags().add(tag);

        final FlashCardDto newFlashCard = client.save(flashCard);

        assertThat(newFlashCard.getId(), Matchers.greaterThan(0L));
        assertThat(newFlashCard.getTags().size(), Matchers.greaterThan(0));

        client.delete(newFlashCard.getId());
    }

    @Test
    public void testFindFlashCardsForTag() throws FlashCardsException
    {
        final FlashCardDto flashCard = givenFlashCardHasTwoTags();

        final List<FlashCardDto> results = client.findFlashCardsForTag(flashCard.getTags().iterator().next().getId(), null);
        assertTrue(results != null);
        assertTrue(results.size() == 1);

        cleanupFlashCard(flashCard);
    }

    private void cleanupFlashCard(FlashCardDto flashCard) {
        final Set<TagDto> tags = flashCard.getTags();
        client.delete(flashCard.getId());
        cleanupTags(tags);
    }

    private void cleanupTags(Set<TagDto> tags) {
        tags.forEach(tag -> tagClient.delete(tag.getId()));
    }

    private String randomQuestion() {
        return randomString("Question", 10);
    }

    private String randomAnwer() {
        return randomString("Answer", 10);
    }

    private String randomTagName() {
        return randomString("Tag", 10);
    }

    private String randomString(final String prefix, final int size) {
        return prefix + ": " + RandomStringUtils.randomAlphabetic(size);
    }
}
