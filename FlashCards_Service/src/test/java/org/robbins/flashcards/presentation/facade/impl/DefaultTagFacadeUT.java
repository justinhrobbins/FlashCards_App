
package org.robbins.flashcards.presentation.facade.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.service.TagService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class DefaultTagFacadeUT extends BaseMockingTest {

    @Mock
    private TagService mockService;

    @Mock
    private TagDto mockTag;

    private TagFacade tagFacade;

    private final Integer PAGE_SIZE = 10;

    @Before
    public void before() {
        tagFacade = new DefaultTagFacade();
        ReflectionTestUtils.setField(tagFacade, "service", mockService);
    }

    @Test
    public void findByName() throws FlashcardsException
	{
        when(mockService.findByName(any(String.class))).thenReturn(mockTag);

        TagDto result = tagFacade.findByName(any(String.class));

        verify(mockService).findByName(any(String.class));
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void findByName_ReturnNull() throws FlashcardsException {
        when(mockService.findByName(any(String.class))).thenReturn(null);

        TagDto result = tagFacade.findByName(any(String.class));

        verify(mockService).findByName(any(String.class));
        assertThat(result, is(nullValue()));
    }

    @Test
    public void count() {
        when(mockService.count()).thenReturn(1L);

        Long result = tagFacade.count();

        verify(mockService).count();
        assertThat(result, is(1L));
    }

    @Test
    public void findOne() throws FlashcardsException {
		Long id = 1L;
        when(mockService.findOne(id, null)).thenReturn(mockTag);

        TagDto result = tagFacade.findOne(id);

        verify(mockService).findOne(id, null);
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void findOne_WithFields() throws FlashcardsException {

        when(mockService.findOne(any(Long.class), any(Set.class))).thenReturn(mockTag);

        TagDto result = tagFacade.findOne(any(Long.class), any(Set.class));

        verify(mockService).findOne(any(Long.class), any(Set.class));
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void findOne_ReturnsNull() throws FlashcardsException {
		Long id = 1L;

        when(mockService.findOne(id, null)).thenReturn(null);

        TagDto result = tagFacade.findOne(id);

        verify(mockService).findOne(id, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void delete() throws ServiceException {
        tagFacade.delete(any(Long.class));

        verify(mockService).delete(any(Long.class));
    }

    @Test
    public void save() throws FlashcardsException {
        when(mockService.save(any(TagDto.class))).thenReturn(mockTag);

        TagDto result = tagFacade.save(mockTag);

        verify(mockService).save(any(TagDto.class));
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void list() throws FlashcardsException {
        List<TagDto> mockTagList = Arrays.asList(mockTag);

        when(mockService.findAll(null, null, null, null, null)).thenReturn(mockTagList);

        List<TagDto> results = tagFacade.list();

        verify(mockService).findAll(null, null, null, null, null);
        assertThat(results, is(List.class));
    }

    @Test
    public void list_WithSortAsc() throws FlashcardsException {
		final String SORT = "SORT";
		final String DIRECTION = "ASC";
        final List<TagDto> mockTagList = Arrays.asList(mockTag);

		when(mockService.findAll(null, null, SORT, DIRECTION, null)).thenReturn(mockTagList);

        List<TagDto> results = tagFacade.list(null, null, SORT, DIRECTION);

		verify(mockService).findAll(null, null, SORT, DIRECTION, null);
        assertThat(results, is(List.class));
    }

    @Test
    public void list_WithSortDesc() throws FlashcardsException {
		final String SORT = "SORT";
		final String DIRECTION = "DESC";
        final List<TagDto> mockTagList = Arrays.asList(mockTag);

		when(mockService.findAll(null, null, SORT, DIRECTION, null)).thenReturn(mockTagList);

        final List<TagDto> results = tagFacade.list(null, null, SORT, DIRECTION);

        verify(mockService).findAll(null, null, SORT, DIRECTION, null);
        assertThat(results, is(List.class));
    }

    @Test
    public void list_ReturnNull() throws FlashcardsException {
        when(mockService.findAll(null, null, null, null, null)).thenReturn(null);

        final List<TagDto> results = tagFacade.list(null, null, null, null);

        verify(mockService).findAll(null, null, null, null, null);
        assertThat(results, is(nullValue()));
    }

    @Test
    public void list_WithPageAndSort() throws FlashcardsException {
		final Integer PAGE = 1;
		final String SORT = "SORT";
		final String DIRECTION = "ASC";
        final List<TagDto> mockTagList = Arrays.asList(mockTag);

        when(mockService.findAll(PAGE, PAGE_SIZE, SORT, DIRECTION, null)).thenReturn(mockTagList);

        final List<TagDto> results = tagFacade.list(PAGE, PAGE_SIZE, SORT, DIRECTION);

        verify(mockService).findAll(PAGE, PAGE_SIZE, SORT, DIRECTION, null);
        assertThat(results, is(List.class));
    }

    @Test
    public void list_WithPageNoSort() throws FlashcardsException {
		final Integer PAGE = 1;
        final List<TagDto> mockTagList = Arrays.asList(mockTag);

		when(mockService.findAll(PAGE, PAGE_SIZE, null, null, null)).thenReturn(mockTagList);

        final List<TagDto> results = tagFacade.list(PAGE, PAGE_SIZE, null, null);

        verify(mockService).findAll(PAGE, PAGE_SIZE, null, null, null);
        assertThat(results, is(List.class));
    }
}
