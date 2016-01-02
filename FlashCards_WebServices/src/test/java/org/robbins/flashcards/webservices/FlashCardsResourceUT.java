
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

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.service.FlashCardService;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class FlashCardsResourceUT extends BaseMockingTest {

    @Mock
    private FlashCardService mockFlashCardService;

    @Mock
    private FlashCardDto mockFlashCardDto;

    private List<FlashCardDto> mockFlashCardDtoList;

    private FlashCardsResource resource;

    private final Long uuid = RandomUtils.nextLong(0L, Long.MAX_VALUE);

    @Before
    public void before() {
        mockFlashCardDtoList = Arrays.asList(new FlashCardDto());

        resource = new FlashCardsResource();
        ReflectionTestUtils.setField(resource, "flashCardService", mockFlashCardService);
    }

    @Test(expected = GenericWebServiceException.class)
    public void search() {
        resource.search(null, null, null, null);
    }

    @Test
    public void searchCount() throws FlashCardsException
	{
        when(mockFlashCardService.findByQuestionLike(any(String.class))).thenReturn(
                mockFlashCardDtoList);

        Long result = resource.searchCount("QUESTION", null);

        verify(mockFlashCardService).findByQuestionLike(any(String.class));
        assertThat(result, is(Long.class));
    }

    @Test
    public void search_byQuestion() throws FlashCardsException
    {
        when(mockFlashCardService.findByQuestionLike(any(String.class))).thenReturn(
                mockFlashCardDtoList);

        FlashCardDto[] results = resource.search(null, null, "QUESTION", null);

        verify(mockFlashCardService).findByQuestionLike(any(String.class));
        assertThat(results, notNullValue());
    }

    @Test
    public void search_byQuestion_withPage() throws FlashCardsException
    {
        when(
                mockFlashCardService.findByQuestionLike(any(String.class),
                        any(PageRequest.class))).thenReturn(
                mockFlashCardDtoList);

        FlashCardDto[] results = resource.search(0, 5, "QUESTION", null);

        verify(mockFlashCardService).findByQuestionLike(any(String.class), any(PageRequest.class));
        assertThat(results, notNullValue());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void search_byTags() throws FlashCardsException
    {
        when(mockFlashCardService.findByTagsIn(any(Set.class))).thenReturn(
                mockFlashCardDtoList);

        FlashCardDto[] results = resource.search(null, null, null, "1,2");

        verify(mockFlashCardService).findByTagsIn(any(Set.class));
        assertThat(results, notNullValue());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void search_byTags_withPage() throws FlashCardsException
    {
        when(mockFlashCardService.findByTagsIn(any(Set.class), any(PageRequest.class))).thenReturn(
                mockFlashCardDtoList);

        FlashCardDto[] results = resource.search(0, 5, null, "1,2");

        verify(mockFlashCardService).findByTagsIn(any(Set.class), any(PageRequest.class));
        assertThat(results, notNullValue());
    }

    @Test
    public void put_withCreatedBy() throws FlashCardsException
    {
        when(mockFlashCardService.findOne(uuid, null)).thenReturn(
                mockFlashCardDto);
        when(mockFlashCardService.save(any(FlashCardDto.class))).thenReturn(
                mockFlashCardDto);

        Response response = resource.put(uuid, mockFlashCardDto);

        verify(mockFlashCardService).save(any(FlashCardDto.class));
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test(expected = GenericWebServiceException.class)
    public void put_withGenericWebServiceException() throws FlashCardsException
    {
        when(mockFlashCardService.findOne(uuid, null)).thenThrow(
                new FlashCardsException("ERROR"));

        resource.put(uuid, mockFlashCardDto);
    }
}
