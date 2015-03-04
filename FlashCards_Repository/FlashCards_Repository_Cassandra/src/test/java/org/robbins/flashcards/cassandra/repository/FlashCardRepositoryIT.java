package org.robbins.flashcards.cassandra.repository;


import com.google.common.collect.Lists;
import org.apache.commons.lang.RandomStringUtils;
import org.cassandraunit.spring.CassandraDataSet;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@CassandraDataSet(value = {"cql/FlashCardRepositoryIT.cql"}, keyspace = "flashcardsapp")
public class FlashCardRepositoryIT extends AbstractCassandraIntegrationTest {

    private final String ID = "0791e3ec-c072-11e4-8dfc-aa07a5b093db";
    private final String QUESTION = "question1";
    private final String ANSWER = "answer1";

    @Autowired
    FlashCardCassandraRepository flashCardRepository;

    @Test
    public void testFindOne() {
        final FlashCardCassandraDto fc = new FlashCardCassandraBuilder()
                .withId(UUID.fromString(ID)).build();

        final FlashCardCassandraDto result = flashCardRepository.findOne(fc.getId());
        assertThat(result.getQuestion(), is(QUESTION));
    }

    @Test
    public void testFindAll() {
        final List<FlashCardCassandraDto> flashcards = Lists.newArrayList(flashCardRepository.findAll());
        assertThat(flashcards.size(), greaterThan(0));
    }

    @Test
    public void testSave() {
        final FlashCardCassandraDto flashcard = new FlashCardCassandraBuilder()
                .withId(UUID.randomUUID())
                .withQuestion("new question")
                .withAnswer("new answer")
                .build();

        FlashCardCassandraDto result = flashCardRepository.save(flashcard);
        assertThat(result.getId(), is(flashcard.getId()));
        assertThat(result.getQuestion(), is(flashcard.getQuestion()));
        assertThat(result.getAnswer(), is(flashcard.getAnswer()));
    }

    @Test
    public void testDelete() {
        FlashCardCassandraDto toDelete = createFlashCard();

        flashCardRepository.delete(toDelete.getId());
    }

    private FlashCardCassandraDto createFlashCard() {
        final FlashCardCassandraDto flashcard = new FlashCardCassandraBuilder()
                .withId(UUID.randomUUID())
                .withQuestion(RandomStringUtils.random(10))
                .withAnswer(RandomStringUtils.random(10))
                .build();

        return flashCardRepository.save(flashcard);
    }
}
