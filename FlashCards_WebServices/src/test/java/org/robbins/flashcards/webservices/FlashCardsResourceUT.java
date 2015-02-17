
package org.robbins.flashcards.webservices;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class FlashCardsResourceUT extends BaseMockingTest {

    @Mock
    private FlashcardFacade mockFlashCardFacade;

    @Mock
    private FlashCardDto mockFlashCardDto;

    private List<FlashCardDto> mockFlashCardDtoList;

    private FlashCardsResource resource;

    private final String uuid = UUID.randomUUID().toString();

    @Before
    public void before() {
        mockFlashCardDtoList = Arrays.asList(new FlashCardDto());

        resource = new FlashCardsResource();
        ReflectionTestUtils.setField(resource, "flashcardFacade", mockFlashCardFacade);
    }

    @Test(expected = GenericWebServiceException.class)
    public void search() {
        resource.search(null, null, null, null);
    }

    @Test
    public void searchCount() throws FlashcardsException
	{
        when(mockFlashCardFacade.findByQuestionLike(any(String.class))).thenReturn(
                mockFlashCardDtoList);

        Long result = resource.searchCount("QUESTION", null);

        verify(mockFlashCardFacade).findByQuestionLike(any(String.class));
        assertThat(result, is(Long.class));
    }

    @Test
    public void search_byQuestion() throws FlashcardsException {
        when(mockFlashCardFacade.findByQuestionLike(any(String.class))).thenReturn(
                mockFlashCardDtoList);

        FlashCardDto[] results = resource.search(null, null, "QUESTION", null);

        verify(mockFlashCardFacade).findByQuestionLike(any(String.class));
        assertThat(results, notNullValue());
    }

    @Test
    public void search_byQuestion_withPage() throws FlashcardsException {
        when(
                mockFlashCardFacade.findByQuestionLike(any(String.class),
                        any(PageRequest.class))).thenReturn(
                mockFlashCardDtoList);

        FlashCardDto[] results = resource.search(0, 5, "QUESTION", null);

        verify(mockFlashCardFacade).findByQuestionLike(any(String.class), any(PageRequest.class));
        assertThat(results, notNullValue());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void search_byTags() throws FlashcardsException {
        when(mockFlashCardFacade.findByTagsIn(any(Set.class))).thenReturn(
                mockFlashCardDtoList);

        FlashCardDto[] results = resource.search(null, null, null, "1,2");

        verify(mockFlashCardFacade).findByTagsIn(any(Set.class));
        assertThat(results, notNullValue());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void search_byTags_withPage() throws FlashcardsException {
        when(mockFlashCardFacade.findByTagsIn(any(Set.class), any(PageRequest.class))).thenReturn(
                mockFlashCardDtoList);

        FlashCardDto[] results = resource.search(0, 5, null, "1,2");

        verify(mockFlashCardFacade).findByTagsIn(any(Set.class), any(PageRequest.class));
        assertThat(results, notNullValue());
    }

    @Test
    public void put_withCreatedBy() throws FlashcardsException {
        when(mockFlashCardFacade.findOne(uuid, null)).thenReturn(
                mockFlashCardDto);
        when(mockFlashCardFacade.save(any(FlashCardDto.class))).thenReturn(
                mockFlashCardDto);

        Response response = resource.put(uuid, mockFlashCardDto);

        verify(mockFlashCardFacade).save(any(FlashCardDto.class));
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test(expected = GenericWebServiceException.class)
    public void put_withGenericWebServiceException() throws FlashcardsException {
        when(mockFlashCardFacade.findOne(uuid, null)).thenThrow(
                new FlashcardsException("ERROR"));

        resource.put(uuid, mockFlashCardDto);
    }
}
