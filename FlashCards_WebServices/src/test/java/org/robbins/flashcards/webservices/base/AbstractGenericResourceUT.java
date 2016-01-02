
package org.robbins.flashcards.webservices.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.service.TagService;
import org.robbins.flashcards.webservices.TagsResource;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import com.sun.jersey.api.JResponse;

@Category(UnitTest.class)
public class AbstractGenericResourceUT extends BaseMockingTest {

    @Mock
    private TagService mockTagService;

    @Mock
    private TagDto mockTagDto;

    private List<TagDto> tagDtoList;

    private TagsResource resource;

    private final Long uuid = RandomUtils.nextLong(0L, Long.MAX_VALUE);

    @Before
    public void before() {
        resource = new TagsResource();
        tagDtoList = Arrays.asList(mockTagDto);

        ReflectionTestUtils.setField(resource, "tagService", mockTagService);
    }

    @Test
    public void list() throws FlashcardsException
	{
        when(mockTagService.findAll(null, null, null, null, null)).thenReturn(tagDtoList);

        JResponse<List<TagDto>> results = resource.list(null, null, null, null, null);

        verify(mockTagService).findAll(null, null, null, null, null);
        assertThat(results.getEntity(), is(List.class));
    }

    @Test
    public void list_NullResult() throws FlashcardsException {
        when(mockTagService.findAll(null, null, null, null)).thenReturn(null);

        JResponse<List<TagDto>> results = resource.list(null, null, null, null, null);

        verify(mockTagService).findAll(null, null, null, null, null);
        assertThat(results.getEntity(), is(List.class));
        assertThat(results.getEntity().size(), is(0));
    }

    @Test(expected = WebApplicationException.class)
    public void listWithInvalidSortParameter() throws FlashcardsException {
        when(mockTagService.findAll(null, null, "bad_parameter", "asc", null)).thenThrow(
                new InvalidDataAccessApiUsageException("error"));

        resource.list(null, null, "bad_parameter", "asc", null);
    }

    @Test
    public void listWithSort() throws FlashcardsException {
        when(mockTagService.findAll(null, null, "name", "asc", null)).thenReturn(tagDtoList);

        JResponse<List<TagDto>> results = resource.list(null, null, "name", "asc", null);

        verify(mockTagService).findAll(null, null, "name", "asc", null);
        assertThat(results.getEntity(), is(List.class));
    }

    @Test
    public void listWithPagingAndSort() throws FlashcardsException {
        when(mockTagService.findAll(0, 1, "name", "desc", null)).thenReturn(tagDtoList);

        JResponse<List<TagDto>> results = resource.list(0, 1, "name", "desc", null);

        verify(mockTagService).findAll(0, 1, "name", "desc", null);
        assertThat(results.getEntity(), is(List.class));
    }

    @Test
    public void listWithPagingNoSort() throws FlashcardsException {
        when(mockTagService.findAll(0, 1, null, null, null)).thenReturn(tagDtoList);

        JResponse<List<TagDto>> results = resource.list(0, 1, null, null, null);

        verify(mockTagService).findAll(0, 1, null, null, null);
        assertThat(results.getEntity(), is(List.class));
    }

    @Test
    public void count() {
        when(mockTagService.count()).thenReturn(1L);

        Long result = resource.count();

        verify(mockTagService).count();
        assertThat(result, is(Long.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findOne() throws FlashcardsException {
        when(mockTagService.findOne(anyLong(), anySet())).thenReturn(new TagDto(uuid));

        TagDto result = resource.findOne(uuid, null);

        verify(mockTagService).findOne(anyLong(), anySet());
        assertThat(result, is(TagDto.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findOne_WithFields() throws FlashcardsException {
        String fields = "name,flashcards,userpassword";
        when(mockTagService.findOne(anyLong(), anySet())).thenReturn(new TagDto(uuid));

        TagDto result = resource.findOne(uuid, fields);

        verify(mockTagService).findOne(anyLong(), anySet());
        assertThat(result, is(TagDto.class));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = GenericWebServiceException.class)
    public void findOne_ReturnsNull() throws FlashcardsException {
        when(mockTagService.findOne(anyLong(), anySet())).thenReturn(null);

        resource.findOne(uuid, null);
    }

    @Test
    public void post() throws FlashcardsException {
        when(mockTagService.save(any(TagDto.class))).thenReturn(new TagDto(uuid));

        TagDto result = resource.post(new TagDto());

        verify(mockTagService).save(any(TagDto.class));
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void delete() {
        Response response = resource.delete(anyLong());

        verify(mockTagService).delete(anyLong());
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void update() throws FlashcardsException {
        when(mockTagService.findOne(any(Long.class))).thenReturn(mockTagDto);
        when(mockTagService.save(any(TagDto.class))).thenReturn(mockTagDto);

        Response response = resource.update(uuid, mockTagDto);

        verify(mockTagService).findOne(any(Long.class));
        verify(mockTagService).save(any(TagDto.class));
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }
}
