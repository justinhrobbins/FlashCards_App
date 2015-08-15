
package org.robbins.flashcards.cassandra.repository.facade.impl;

import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.AbstractCassandraIntegrationTest;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.builder.FlashCardDtoBuilder;
import org.robbins.flashcards.dto.builder.TagDtoBuilder;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class DefaultFlashCardFacadeIT extends AbstractCassandraIntegrationTest {

    private final String ID = "0791e3ec-c072-11e4-8dfc-aa07a5b093db";
    final String NEW_QUESTION = "new question";
    final String NEW_ANSWER = "new answer";

    @Inject
    private FlashcardFacade facade;

    @Test
    public void testList() throws FlashcardsException
	{
        final List<FlashCardDto> results = facade.list();

        assertThat(results, is(notNullValue()));
        assertThat(results.size(), greaterThan(0));
    }

    @Test
    public void testFindOne() throws FlashcardsException {

        final FlashCardDto result = facade.findOne(ID);
        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(ID));
    }

    @Test
    public void testFindOne_ShouldReturnNull_WhenIdDoesNotExist() throws FlashcardsException {

        final FlashCardDto result = facade.findOne(UUID.randomUUID().toString());
        assertThat(result, is(nullValue()));
    }

    @Test
    public void testSave() throws FlashcardsException
    {
        final FlashCardDto dto = new FlashCardDtoBuilder()
                .withQuestion(NEW_QUESTION)
                .withAnswer(NEW_ANSWER)
                .build();
        final FlashCardDto result =  facade.save(dto);

        assertThat(result, is(notNullValue()));
        assertThat(result.getQuestion(), is(NEW_QUESTION));
        assertThat(result.getAnswer(), is(NEW_ANSWER));
    }

    @Test
    public void testSave_WithExistingTags() throws FlashcardsException
    {
        final FlashCardDto dto = new FlashCardDtoBuilder()
                .withQuestion(NEW_QUESTION)
                .withAnswer(NEW_ANSWER)
                .withTag(testUtils.createTagDto())
                .withTag(testUtils.createTagDto())
                .build();
        final FlashCardDto result =  facade.save(dto);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTags().size(), is(2));
    }

    @Test
    public void testSave_WithNewTags() throws FlashcardsException
    {
        final FlashCardDto dto = new FlashCardDtoBuilder()
                .withQuestion(NEW_QUESTION)
                .withAnswer(NEW_ANSWER)
                .withTag(new TagDtoBuilder().withName("newtag1").build())
                .withTag(new TagDtoBuilder().withName("newtag2").build())
                .build();
        final FlashCardDto result =  facade.save(dto);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTags().size(), is(2));
    }

    @Test
    public void testSave_WithNewAndExistingTag() throws FlashcardsException
    {
        final FlashCardDto dto = new FlashCardDtoBuilder()
                .withQuestion(NEW_QUESTION)
                .withAnswer(NEW_ANSWER)
                .withTag(new TagDtoBuilder().withName("newtag1").build())
                .withTag(testUtils.createTagDto())
                .build();
        final FlashCardDto result =  facade.save(dto);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTags().size(), is(2));
    }

    @Test
    public void testUpdate() throws FlashcardsException
    {
        final String UPDATED_QUESTION = "updated question";
        final FlashCardDto toUpdate = testUtils.createFlashCardDto();
        toUpdate.setQuestion(UPDATED_QUESTION);

        final FlashCardDto result =  facade.save(toUpdate);

        assertThat(result, is(notNullValue()));
        assertThat(result.getQuestion(), is(UPDATED_QUESTION));
    }

    @Test
    public void testDelete() throws FlashcardsException {
        final FlashCardDto toDelete = testUtils.createFlashCardDto();

        facade.delete(toDelete.getId());
    }
}
