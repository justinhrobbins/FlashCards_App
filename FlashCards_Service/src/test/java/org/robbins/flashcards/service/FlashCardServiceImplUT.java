
package org.robbins.flashcards.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class FlashCardServiceImplUT extends BaseMockingTest {

    @Mock
    private FlashcardFacade facade;

    private FlashCardServiceImpl flashCardService;

    @Before
    public void before() {
        flashCardService = new FlashCardServiceImpl();
        ReflectionTestUtils.setField(flashCardService, "facade", facade);
    }

    @Test
    public void testFindByQuestion() throws FlashcardsException {
        when(facade.findByQuestion(Matchers.anyString())).thenReturn(
                mock(FlashCardDto.class));

        FlashCardDto flashCard = flashCardService.findByQuestion("Question");

        Mockito.verify(facade, Mockito.times(1)).findByQuestion("Question");
        assertThat(flashCard, is(FlashCardDto.class));
    }

    @Test
    public void testFindByQuestionLike() throws FlashcardsException {
        when(facade.findByQuestionLike(Matchers.anyString())).thenReturn(
                new ArrayList<>());

        List<FlashCardDto> flashCards = flashCardService.findByQuestionLike("Question");

        Mockito.verify(facade, Mockito.times(1)).findByQuestionLike("Question");
        assertThat(flashCards, is(List.class));
    }

    @Test
    public void testFindByTagsIn() throws FlashcardsException {
        when(facade.findByTagsIn(new HashSet<>())).thenReturn(
                new ArrayList<>());

        Set<TagDto> tags = new HashSet<>();
        tags.add(new TagDto(1L));

        List<FlashCardDto> flashCards = flashCardService.findByTagsIn(tags);

        Mockito.verify(facade, Mockito.times(1)).findByTagsIn(tags);
        assertThat(flashCards, is(List.class));
    }

    @Test
    public void testFindByTagsInWithPageRequest() throws FlashcardsException {
        when(facade.findByTagsIn(new HashSet<>(), mock(PageRequest.class))).thenReturn(
                new ArrayList<>());

        Set<TagDto> tags = new HashSet<>();
        tags.add(new TagDto(1L));
        PageRequest page = mock(PageRequest.class);

        List<FlashCardDto> flashCards = flashCardService.findByTagsIn(tags, page);

        Mockito.verify(facade, Mockito.times(1)).findByTagsIn(tags, page);
        assertThat(flashCards, is(List.class));
    }

    @Test
    public void testFindByQuestionLikeWithPageRequest() throws FlashcardsException {
        when(facade.findByQuestionLike("Question", mock(PageRequest.class))).thenReturn(
                new ArrayList<>());

        PageRequest page = mock(PageRequest.class);

        List<FlashCardDto> flashCards = flashCardService.findByQuestionLike("Question", page);

        Mockito.verify(facade, Mockito.times(1)).findByQuestionLike("Question", page);
        assertThat(flashCards, is(List.class));
    }
}
