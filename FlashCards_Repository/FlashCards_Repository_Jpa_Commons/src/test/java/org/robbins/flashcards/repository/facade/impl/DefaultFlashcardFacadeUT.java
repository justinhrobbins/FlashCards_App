
package org.robbins.flashcards.repository.facade.impl;

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
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.conversion.DtoConverter;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class DefaultFlashcardFacadeUT extends BaseMockingTest {

    @Mock
    private FlashCardRepository repository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private DtoConverter<FlashCardDto, FlashCard> mockFlashcardConverter;

    @Mock
    private DtoConverter<TagDto, Tag> mockTagConverter;

    @Mock
    private FlashCard mockFlashcard;

    @Mock
    private FlashCardDto mockFlashcardDto;

    @Mock
    private TagDto mockTagDto;

    @Mock
    private Tag mockTag;

    private FlashcardFacade flashCardFacade;

    @Before
    public void before() {
        flashCardFacade = new DefaultFlashcardRepositoryFacade();
        ReflectionTestUtils.setField(flashCardFacade, "repository", repository);
        ReflectionTestUtils.setField(flashCardFacade, "flashcardConverter", mockFlashcardConverter);
        ReflectionTestUtils.setField(flashCardFacade, "tagConverter", mockTagConverter);
        ReflectionTestUtils.setField(flashCardFacade, "tagRepository", tagRepository);
    }

    @Test
    public void findByQuestion() throws FlashcardsException
	{
        when(repository.findByQuestion(any(String.class))).thenReturn(mockFlashcard);
        when(mockFlashcardConverter.getDto(mockFlashcard)).thenReturn(
                mockFlashcardDto);

        FlashCardDto result = flashCardFacade.findByQuestion(any(String.class));

        verify(repository).findByQuestion(any(String.class));
        assertThat(result, is(FlashCardDto.class));
    }

    @Test
    public void findByQuestionLike() throws FlashcardsException {
        List<FlashCard> mockFlashCardList = Arrays.asList(mockFlashcard);

        when(repository.findByQuestionLike(any(String.class))).thenReturn(
				mockFlashCardList);
        when(mockFlashcardConverter.getDto(mockFlashcard)).thenReturn(
                mockFlashcardDto);

        List<FlashCardDto> results = flashCardFacade.findByQuestionLike(any(String.class));

        verify(repository).findByQuestionLike(any(String.class));
        assertThat(results, is(List.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findByTagsIn() throws FlashcardsException {
        List<FlashCard> mockFlashCardList = Arrays.asList(mockFlashcard);
        Set<TagDto> mockTagDtos = new HashSet<>(Arrays.asList(mockTagDto));
        List<Tag> mockTagList = Arrays.asList(mockTag);

        when(repository.findByTagsIn(any(Set.class))).thenReturn(mockFlashCardList);
        when(mockFlashcardConverter.getDto(mockFlashcard)).thenReturn(
                mockFlashcardDto);
        when(mockTagConverter.getEtnties(any(List.class))).thenReturn(mockTagList);

        List<FlashCardDto> results = flashCardFacade.findByTagsIn(mockTagDtos);

        verify(repository).findByTagsIn(any(Set.class));
        assertThat(results, is(List.class));
    }

    @Test
    public void save() throws FlashcardsException {

        when(repository.save(any(FlashCard.class))).thenReturn(mockFlashcard);
        when(tagRepository.findByName(any(String.class))).thenReturn(mockTag);
        when(mockFlashcardConverter.getEntity(mockFlashcardDto)).thenReturn(mockFlashcard);
        when(mockFlashcardConverter.getDto(mockFlashcard)).thenReturn(mockFlashcardDto);
        when(mockFlashcardDto.getTags()).thenReturn(
                new HashSet<>(Arrays.asList(mockTagDto)));

        FlashCardDto result = flashCardFacade.save(mockFlashcardDto);

        verify(repository).save(any(FlashCard.class));
        assertThat(result, is(FlashCardDto.class));
    }
}
