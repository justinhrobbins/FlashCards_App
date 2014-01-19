
package org.robbins.flashcards.facade.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.FlashCardService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class DefaultFlashcardFacadeUT extends BaseMockingTest {

    @Mock
    private FlashCardService mockService;

    @Mock
    private Mapper mockMapper;

    @Mock
    private FlashCard mockFlashcard;

    @Mock
    private FlashCardDto mockFlashcardDto;

    @Mock
    private TagDto mockTagDto;

    @Mock
    private Tag mockTag;

    @Mock
    private TagFacade mockTagFacade;

    private FlashcardFacade flashCardFacade;

    @Before
    public void before() {
        flashCardFacade = new DefaultFlashcardFacade();
        ReflectionTestUtils.setField(flashCardFacade, "flashcardService", mockService);
        ReflectionTestUtils.setField(flashCardFacade, "mapper", mockMapper);
        ReflectionTestUtils.setField(flashCardFacade, "tagFacade", mockTagFacade);
    }

    @Test
    public void findByQuestion() throws ServiceException {
        when(mockService.findByQuestion(any(String.class))).thenReturn(mockFlashcard);
        when(mockMapper.map(mockFlashcard, FlashCardDto.class)).thenReturn(
                mockFlashcardDto);

        FlashCardDto result = flashCardFacade.findByQuestion(any(String.class));

        verify(mockService).findByQuestion(any(String.class));
        assertThat(result, is(FlashCardDto.class));
    }

    @Test
    public void findByQuestionLike() throws ServiceException {
        List<FlashCard> mockFlashCardList = Arrays.asList(mockFlashcard);

        when(mockService.findByQuestionLike(any(String.class))).thenReturn(
                mockFlashCardList);
        when(mockMapper.map(mockFlashcard, FlashCardDto.class)).thenReturn(
                mockFlashcardDto);

        List<FlashCardDto> results = flashCardFacade.findByQuestionLike(any(String.class));

        verify(mockService).findByQuestionLike(any(String.class));
        assertThat(results, is(List.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findByTagsIn() throws ServiceException {
        List<FlashCard> mockFlashCardList = Arrays.asList(mockFlashcard);
        Set<TagDto> mockTagDtos = new HashSet<TagDto>(Arrays.asList(mockTagDto));
        List<Tag> mockTagList = Arrays.asList(mockTag);

        when(mockService.findByTagsIn(any(Set.class))).thenReturn(mockFlashCardList);
        when(mockMapper.map(mockFlashcard, FlashCardDto.class)).thenReturn(
                mockFlashcardDto);
        when(mockTagFacade.getEtnties(any(List.class))).thenReturn(mockTagList);

        List<FlashCardDto> results = flashCardFacade.findByTagsIn(mockTagDtos);

        verify(mockService).findByTagsIn(any(Set.class));
        assertThat(results, is(List.class));
    }

    @Test
    public void save() throws ServiceException {

        when(mockService.save(any(FlashCard.class))).thenReturn(mockFlashcard);
        when(mockMapper.map(mockFlashcardDto, FlashCard.class)).thenReturn(mockFlashcard);
        when(mockMapper.map(mockFlashcard, FlashCardDto.class)).thenReturn(
                mockFlashcardDto);
        when(mockFlashcardDto.getTags()).thenReturn(
                new HashSet<TagDto>(Arrays.asList(mockTagDto)));

        FlashCardDto result = flashCardFacade.save(mockFlashcardDto);

        verify(mockService).save(any(FlashCard.class));
        assertThat(result, is(FlashCardDto.class));
    }

    @Test
    public void getEntities() {
        List<FlashCardDto> flashCardDtos = Arrays.asList(mockFlashcardDto);
        List<FlashCard> results = flashCardFacade.getEtnties(flashCardDtos);

        assertThat(results, is(List.class));
    }
}
