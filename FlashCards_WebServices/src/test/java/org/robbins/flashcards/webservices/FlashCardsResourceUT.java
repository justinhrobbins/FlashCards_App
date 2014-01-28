
package org.robbins.flashcards.webservices;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class FlashCardsResourceUT extends BaseMockingTest {

    @Mock
    private FlashcardFacade flashcardFacade;

    @Mock
    private FlashCardDto flashcard;

    private FlashCardsResource resource;

    @Before
    public void before() {
        resource = new FlashCardsResource();
        ReflectionTestUtils.setField(resource, "flashcardFacade", flashcardFacade);
    }

    @Test
    public void searchByQuestionWithPaging() throws ServiceException {
        when(
                flashcardFacade.findByQuestionLike(any(String.class),
                        any(PageRequest.class))).thenReturn(new ArrayList<FlashCardDto>());

        FlashCardDto[] results = resource.search(0, 1, "Question", null);

        verify(flashcardFacade).findByQuestionLike(any(String.class),
                any(PageRequest.class));
        assertThat(results, is(FlashCardDto[].class));
    }

    @Test
    public void searchByQuestionNoPaging() throws ServiceException {
        when(flashcardFacade.findByQuestionLike(any(String.class))).thenReturn(
                new ArrayList<FlashCardDto>());

        FlashCardDto[] results = resource.search(null, null, "Question", null);

        verify(flashcardFacade).findByQuestionLike(any(String.class));
        assertThat(results, is(FlashCardDto[].class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void searchByTagsWithPaging() throws ServiceException {
        when(flashcardFacade.findByTagsIn(any(Set.class), any(PageRequest.class))).thenReturn(
                new ArrayList<FlashCardDto>());

        FlashCardDto[] results = resource.search(0, 1, null, "1,2");

        verify(flashcardFacade).findByTagsIn(any(Set.class), any(PageRequest.class));
        assertThat(results, is(FlashCardDto[].class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void searchByTagsNoPaging() throws ServiceException {
        when(flashcardFacade.findByTagsIn(any(Set.class))).thenReturn(
                new ArrayList<FlashCardDto>());

        FlashCardDto[] results = resource.search(null, null, null, "1,2");

        verify(flashcardFacade).findByTagsIn(any(Set.class));
        assertThat(results, is(FlashCardDto[].class));
    }

    @Test(expected = WebApplicationException.class)
    public void searchWithBadRequest() {
        resource.search(null, null, null, null);
    }

    @Test
    public void searchCount() throws ServiceException {
        when(flashcardFacade.findByQuestionLike(any(String.class))).thenReturn(
                new ArrayList<FlashCardDto>());

        Long results = resource.searchCount("Question", null);

        verify(flashcardFacade).findByQuestionLike(any(String.class));
        assertThat(results, is(Long.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void put() throws ServiceException {
        when(flashcardFacade.findOne(any(Long.class), any(Set.class))).thenReturn(
                flashcard);
        when(flashcardFacade.save(any(FlashCardDto.class))).thenReturn(flashcard);

        Response response = resource.put(1L, flashcard);

        verify(flashcardFacade).findOne(any(Long.class), any(Set.class));
        verify(flashcardFacade).save(any(FlashCardDto.class));
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void postNoTags() throws ServiceException {
        when(flashcardFacade.save(any(FlashCardDto.class))).thenReturn(flashcard);

        FlashCardDto result = resource.post(flashcard);

        verify(flashcardFacade).save(any(FlashCardDto.class));
        assertThat(result, is(FlashCardDto.class));
    }

    @Test
    public void postWithTagByName() throws ServiceException {
        when(flashcardFacade.save(any(FlashCardDto.class))).thenReturn(flashcard);

        FlashCardDto flashcard = new FlashCardDto();
        flashcard.getTags().add(new TagDto("tag1"));
        FlashCardDto result = resource.post(flashcard);

        verify(flashcardFacade).save(any(FlashCardDto.class));
        assertThat(result, is(FlashCardDto.class));
    }

    @Test
    public void postWithNewTagByName() throws ServiceException {
        when(flashcardFacade.save(any(FlashCardDto.class))).thenReturn(flashcard);

        FlashCardDto flashcard = new FlashCardDto();
        flashcard.getTags().add(new TagDto("tag1"));
        FlashCardDto result = resource.post(flashcard);

        verify(flashcardFacade).save(any(FlashCardDto.class));
        assertThat(result, is(FlashCardDto.class));
    }

    @Test
    public void postWithTagById() throws ServiceException {
        when(flashcardFacade.save(any(FlashCardDto.class))).thenReturn(flashcard);

        FlashCardDto flashcard = new FlashCardDto();
        flashcard.getTags().add(new TagDto(1L));
        FlashCardDto result = resource.post(flashcard);

        verify(flashcardFacade).save(any(FlashCardDto.class));
        assertThat(result, is(FlashCardDto.class));
    }
}
