
package org.robbins.flashcards.repository.springdata;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.robbins.tests.BaseIntegrationTest;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import com.github.springtestdbunit.annotation.DatabaseSetup;

@ContextConfiguration("classpath:META-INF/test-applicationContext-repository-springdata.xml")
@DatabaseSetup("classpath:test-flashCardsAppRepository.xml")
@Category(IntegrationTest.class)
public class FlashCardRepositoryIT extends BaseIntegrationTest {

    @Inject
    private FlashCardRepository<FlashCard, Tag, Long> repository;

    @Test
    public void findByQuestion() {
        FlashCard flashCard = repository.findByQuestion("question1");
        assertThat(flashCard, is(instanceOf(FlashCard.class)));
    }

    @Test
    public void createNewFlashCard_WithNewTag() {
        FlashCard flashCard = new FlashCard();
        flashCard.setQuestion("Question2");
        flashCard.setAnswer("Answer2");
        flashCard.setCreatedBy(1L);

        Tag tag = new Tag();
        tag.setName("tag3");
        tag.setCreatedBy(1L);

        flashCard.getTags().add(tag);

        repository.save(flashCard);

        assertThat(flashCard.getId(), greaterThan(0L));
    }
}
