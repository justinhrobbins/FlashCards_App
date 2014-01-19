
package org.robbins.flashcards.service.springdata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.springdata.FlashCardRepository;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class FlashCardServiceImplUT extends BaseMockingTest {

    @Mock
    FlashCardRepository repository;

    FlashCardServiceImpl flashCardService;

    @Mock
    FlashCard flashcard;

    @Mock
    List<FlashCard> flashcards;

    @Mock
    PageRequest page;

    @Before
    public void before() {
        flashCardService = new FlashCardServiceImpl();
        ReflectionTestUtils.setField(flashCardService, "repository", repository);
    }

    @Test
    public void testFindByQuestion() {
        when(repository.findByQuestion(Matchers.anyString())).thenReturn(flashcard);

        FlashCard result = flashCardService.findByQuestion("Question");

        Mockito.verify(repository, Mockito.times(1)).findByQuestion("Question");
        assertThat(result, is(FlashCard.class));
    }

    @Test
    public void testFindByQuestionLike() {
        when(repository.findByQuestionLike(Matchers.anyString())).thenReturn(flashcards);

        List<FlashCard> results = flashCardService.findByQuestionLike("Question");

        Mockito.verify(repository, Mockito.times(1)).findByQuestionLike("Question");
        assertThat(results, is(List.class));
    }

    @Test
    public void testFindByTagsIn() {
        when(repository.findByTagsIn(new HashSet<Tag>())).thenReturn(flashcards);

        Set<Tag> tags = new HashSet<Tag>();
        tags.add(new Tag("EJB"));

        List<FlashCard> results = flashCardService.findByTagsIn(tags);

        Mockito.verify(repository, Mockito.times(1)).findByTagsIn(tags);
        assertThat(results, is(List.class));
    }

    @Test
    public void testFindByTagsInWithPageRequest() {
        when(repository.findByTagsIn(new HashSet<Tag>(), mock(PageRequest.class))).thenReturn(
                flashcards);

        Set<Tag> tags = new HashSet<Tag>();
        tags.add(new Tag("EJB"));
        PageRequest page = mock(PageRequest.class);

        List<FlashCard> results = flashCardService.findByTagsIn(tags, page);

        Mockito.verify(repository, Mockito.times(1)).findByTagsIn(tags, page);
        assertThat(results, is(List.class));
    }

    @Test
    public void testFindByQuestionLikeWithPageRequest() {
        when(repository.findByQuestionLike("Question", page)).thenReturn(flashcards);

        List<FlashCard> results = flashCardService.findByQuestionLike("Question", page);

        Mockito.verify(repository, Mockito.times(1)).findByQuestionLike("Question", page);
        assertThat(results, is(List.class));
    }
}
