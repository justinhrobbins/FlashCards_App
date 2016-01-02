
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
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.FlashCardFacade;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.repository.auditing.AuditingAwareUser;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class DefaultFlashCardFacadeUT extends BaseMockingTest {

    @Mock
    private FlashCardRepository repository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private DtoConverter<FlashCardDto, FlashCard> mockFlashCardConverter;

    @Mock
    private DtoConverter<TagDto, Tag> mockTagConverter;

    @Mock
    private FlashCard mockFlashCard;

    @Mock
    private FlashCardDto mockFlashCardDto;

    @Mock
    private TagDto mockTagDto;

    @Mock
    private Tag mockTag;

    @Mock
    private AuditingAwareUser auditorAware;

    private FlashCardFacade flashCardFacade;

    @Before
    public void before() {
        flashCardFacade = new DefaultFlashCardRepositoryFacade();
        ReflectionTestUtils.setField(flashCardFacade, "repository", repository);
        ReflectionTestUtils.setField(flashCardFacade, "flashCardConverter", mockFlashCardConverter);
        ReflectionTestUtils.setField(flashCardFacade, "tagConverter", mockTagConverter);
        ReflectionTestUtils.setField(flashCardFacade, "tagRepository", tagRepository);
        ReflectionTestUtils.setField(flashCardFacade, "auditorAware", auditorAware);
    }

    @Test
    public void findByQuestion() throws FlashCardsException
	{
        when(repository.findByQuestion(any(String.class))).thenReturn(mockFlashCard);
        when(mockFlashCardConverter.getDto(mockFlashCard, null)).thenReturn(
                mockFlashCardDto);

        FlashCardDto result = flashCardFacade.findByQuestion(any(String.class));

        verify(repository).findByQuestion(any(String.class));
        assertThat(result, is(FlashCardDto.class));
    }

    @Test
    public void findByQuestionLike() throws FlashCardsException
    {
        List<FlashCard> mockFlashCardList = Arrays.asList(mockFlashCard);

        when(repository.findByQuestionLike(any(String.class))).thenReturn(
				mockFlashCardList);
        when(mockFlashCardConverter.getDto(mockFlashCard)).thenReturn(
                mockFlashCardDto);

        List<FlashCardDto> results = flashCardFacade.findByQuestionLike(any(String.class));

        verify(repository).findByQuestionLike(any(String.class));
        assertThat(results, is(List.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findByTagsIn() throws FlashCardsException
    {
        List<FlashCard> mockFlashCardList = Arrays.asList(mockFlashCard);
        Set<TagDto> mockTagDtos = new HashSet<>(Arrays.asList(mockTagDto));
        List<Tag> mockTagList = Arrays.asList(mockTag);

        when(repository.findByTagsIn(any(Set.class))).thenReturn(mockFlashCardList);
        when(mockFlashCardConverter.getDto(mockFlashCard)).thenReturn(
                mockFlashCardDto);
        when(mockTagConverter.getEntities(any(List.class))).thenReturn(mockTagList);

        List<FlashCardDto> results = flashCardFacade.findByTagsIn(mockTagDtos);

        verify(repository).findByTagsIn(any(Set.class));
        assertThat(results, is(List.class));
    }

    @Test
    public void save() throws FlashCardsException
    {

        when(repository.save(any(FlashCard.class))).thenReturn(mockFlashCard);
        when(tagRepository.findByName(any(String.class))).thenReturn(mockTag);
        when(mockFlashCardConverter.getEntity(mockFlashCardDto)).thenReturn(mockFlashCard);
        when(mockFlashCardConverter.getDto(mockFlashCard, null)).thenReturn(mockFlashCardDto);
        when(mockFlashCardDto.getTags()).thenReturn(
                new HashSet<>(Arrays.asList(mockTagDto)));

        FlashCardDto result = flashCardFacade.save(mockFlashCardDto);

        verify(repository).save(any(FlashCard.class));
        assertThat(result, is(FlashCardDto.class));
    }
}
