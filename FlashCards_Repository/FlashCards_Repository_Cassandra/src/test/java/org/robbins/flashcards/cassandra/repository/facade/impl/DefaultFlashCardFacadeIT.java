
package org.robbins.flashcards.cassandra.repository.facade.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.cassandraunit.spring.CassandraDataSet;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.AbstractCassandraIntegrationTest;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.FlashCardDtoBuilder;
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

@CassandraDataSet(value = {"cql/FlashCardRepositoryIT.cql"}, keyspace = "flashcardsapp")
public class DefaultFlashCardFacadeIT extends AbstractCassandraIntegrationTest {

    private final String ID = "0791e3ec-c072-11e4-8dfc-aa07a5b093db";
    private final String QUESTION = "question1";
    private final String ANSWER = "answer1";

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
        final String QUESTION = "new question";
        final String ANSWER = "new answer";
        final FlashCardDto dto = new FlashCardDtoBuilder()
                .withQuestion(QUESTION)
                .withAnswer(ANSWER)
                .build();
        final FlashCardDto result =  facade.save(dto);

        assertThat(result, is(notNullValue()));
        assertThat(result.getQuestion(), is(QUESTION));
        assertThat(result.getAnswer(), is(ANSWER));
    }

    @Test
    public void testUpdate() throws FlashcardsException
    {
        final String UPDATED_QUESTION = "updated question";
        final FlashCardDto toUpdate = createFlashCard();
        toUpdate.setQuestion(UPDATED_QUESTION);

        final FlashCardDto result =  facade.save(toUpdate);

        assertThat(result, is(notNullValue()));
        assertThat(result.getQuestion(), is(UPDATED_QUESTION));
    }

    @Test
    public void testDelete() throws FlashcardsException {
        final FlashCardDto toDelete = createFlashCard();

        facade.delete(toDelete.getId());
    }

    private FlashCardDto createFlashCard() throws FlashcardsException {
        final FlashCardDto dto = new FlashCardDtoBuilder()
                .withQuestion(RandomStringUtils.random(10))
                .withAnswer(RandomStringUtils.random(10))
                .build();

        return facade.save(dto);
    }
}
