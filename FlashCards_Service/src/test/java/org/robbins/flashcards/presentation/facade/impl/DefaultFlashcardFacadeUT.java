
package org.robbins.flashcards.presentation.facade.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.service.FlashCardService;
import org.robbins.flashcards.service.TagService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class DefaultFlashcardFacadeUT extends BaseMockingTest {

    @Mock
    private FlashCardService mockService;

    @Mock
    private TagService mockTagService;

    @Mock
    private FlashCardDto mockFlashcardDto;

    @Mock
    private TagDto mockTagDto;

    private FlashcardFacade flashCardFacade;

    @Before
    public void before() {
        flashCardFacade = new DefaultFlashcardFacade();
        ReflectionTestUtils.setField(flashCardFacade, "flashcardService", mockService);
        ReflectionTestUtils.setField(flashCardFacade, "tagService", mockTagService);
    }

    @Test
    public void findByQuestion() throws FlashcardsException
	{
        when(mockService.findByQuestion(any(String.class))).thenReturn(mockFlashcardDto);

        FlashCardDto result = flashCardFacade.findByQuestion(any(String.class));

        verify(mockService).findByQuestion(any(String.class));
        assertThat(result, is(FlashCardDto.class));
    }

    @Test
    public void findByQuestionLike() throws FlashcardsException {
        List<FlashCardDto> mockFlashCardList = Arrays.asList(mockFlashcardDto);

        when(mockService.findByQuestionLike(any(String.class))).thenReturn(
                mockFlashCardList);

        List<FlashCardDto> results = flashCardFacade.findByQuestionLike(any(String.class));

        verify(mockService).findByQuestionLike(any(String.class));
        assertThat(results, is(List.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findByTagsIn() throws FlashcardsException {
        List<FlashCardDto> mockFlashCardList = Arrays.asList(mockFlashcardDto);
        Set<TagDto> mockTagDtos = new HashSet<>(Arrays.asList(mockTagDto));

        when(mockService.findByTagsIn(any(Set.class))).thenReturn(mockFlashCardList);

        List<FlashCardDto> results = flashCardFacade.findByTagsIn(mockTagDtos);

        verify(mockService).findByTagsIn(any(Set.class));
        assertThat(results, is(List.class));
    }

    @Test
    public void save() throws FlashcardsException {

        when(mockService.save(any(FlashCardDto.class))).thenReturn(mockFlashcardDto);
        when(mockTagService.findByName(any(String.class))).thenReturn(mockTagDto);
        when(mockFlashcardDto.getTags()).thenReturn(
                new HashSet<>(Arrays.asList(mockTagDto)));

        FlashCardDto result = flashCardFacade.save(mockFlashcardDto);

        verify(mockService).save(any(FlashCardDto.class));
        assertThat(result, is(FlashCardDto.class));
    }
}
