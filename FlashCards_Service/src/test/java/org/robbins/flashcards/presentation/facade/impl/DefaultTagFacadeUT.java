
package org.robbins.flashcards.presentation.facade.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
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
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.service.TagService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        when(mockService.findOne(any(Long.class))).thenReturn(mockTag);

        TagDto result = tagFacade.findOne(any(Long.class));

        verify(mockService).findOne(any(Long.class));
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void findOne_WithFields() throws FlashcardsException {
        TagDto tagDto = new TagDto();
        Set<String> fields = new HashSet<String>(Arrays.asList("flashcards"));

        when(mockService.findOne(any(Long.class))).thenReturn(mockTag);

        TagDto result = tagFacade.findOne(any(Long.class), fields);

        verify(mockService).findOne(any(Long.class));
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void findOne_ReturnsNull() throws FlashcardsException {
        when(mockService.findOne(any(Long.class))).thenReturn(null);

        TagDto result = tagFacade.findOne(any(Long.class));

        verify(mockService).findOne(any(Long.class));
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

        when(mockService.findAll()).thenReturn(mockTagList);

        List<TagDto> results = tagFacade.list(null, null, null, null);

        verify(mockService).findAll();
        assertThat(results, is(List.class));
    }

    @Test
    public void list_WithSortAsc() throws FlashcardsException {
        List<TagDto> mockTagList = Arrays.asList(mockTag);

        when(mockService.findAll(any(Sort.class))).thenReturn(mockTagList);

        List<TagDto> results = tagFacade.list(null, null, "name", "asc");

        verify(mockService).findAll(any(Sort.class));
        assertThat(results, is(List.class));
    }

    @Test
    public void list_WithSortDesc() throws FlashcardsException {
        List<TagDto> mockTagList = Arrays.asList(mockTag);

        when(mockService.findAll(any(Sort.class))).thenReturn(mockTagList);

        List<TagDto> results = tagFacade.list(null, null, "name", "desc");

        verify(mockService).findAll(any(Sort.class));
        assertThat(results, is(List.class));
    }

    @Test
    public void list_ReturnNull() throws FlashcardsException {
        when(mockService.findAll()).thenReturn(null);

        List<TagDto> results = tagFacade.list(null, null, null, null);

        verify(mockService).findAll();
        assertThat(results, is(nullValue()));
    }

    @Test
    public void list_WithPageAndSort() throws FlashcardsException {
        List<TagDto> mockTagList = Arrays.asList(mockTag);

        when(mockService.findAll(any(PageRequest.class))).thenReturn(mockTagList);

        List<TagDto> results = tagFacade.list(1, PAGE_SIZE, "name", "asc");

        verify(mockService).findAll(any(PageRequest.class));
        assertThat(results, is(List.class));
    }

    @Test
    public void list_WithPageNoSort() throws FlashcardsException {
        List<TagDto> mockTagList = Arrays.asList(mockTag);

        when(mockService.findAll(any(PageRequest.class))).thenReturn(mockTagList);

        List<TagDto> results = tagFacade.list(1, PAGE_SIZE, null, null);

        verify(mockService).findAll(any(PageRequest.class));
        assertThat(results, is(List.class));
    }
}
