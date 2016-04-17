
package org.robbins.flashcards.webservices.base;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.service.TagService;
import org.robbins.flashcards.webservices.TagsResource;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void list() throws FlashCardsException
	{
        when(mockTagService.findAll(any(Optional.class), anySetOf(String.class))).thenReturn(tagDtoList);

        final Response results = resource.list(null, null, null, null, null);

        verify(mockTagService).findAll(any(Optional.class), anySetOf(String.class));
        assertThat(results.getEntity(), instanceOf(List.class));
    }

    @Test
    public void list_NullResult() throws FlashCardsException
    {
        when(mockTagService.findAll(any(Optional.class), anySetOf(String.class))).thenReturn(null);

        final Response results = resource.list(null, null, null, null, null);

        verify(mockTagService).findAll(any(Optional.class), anySetOf(String.class));
        assertThat(results.getEntity(), instanceOf(List.class));
        assertThat(((List)results.getEntity()).size(), is(0));
    }

    @Test
    public void count() {
        when(mockTagService.count()).thenReturn(1L);

        final Long result = resource.count();

        verify(mockTagService).count();
        assertThat(result, is(Long.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findOne() throws FlashCardsException
    {
        when(mockTagService.findOne(anyLong(), anySet())).thenReturn(new TagDto(uuid));

        final TagDto result = resource.findOne(uuid, null);

        verify(mockTagService).findOne(anyLong(), anySet());
        assertThat(result, is(TagDto.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findOne_WithFields() throws FlashCardsException
    {
        final String fields = "name,flashcards,userpassword";
        when(mockTagService.findOne(anyLong(), anySet())).thenReturn(new TagDto(uuid));

        final TagDto result = resource.findOne(uuid, fields);

        verify(mockTagService).findOne(anyLong(), anySet());
        assertThat(result, is(TagDto.class));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = GenericWebServiceException.class)
    public void findOne_ReturnsNull() throws FlashCardsException
    {
        when(mockTagService.findOne(anyLong(), anySet())).thenReturn(null);

        resource.findOne(uuid, null);
    }

    @Test
    public void post() throws FlashCardsException
    {
        when(mockTagService.save(any(TagDto.class))).thenReturn(new TagDto(uuid));

        final TagDto result = resource.post(new TagDto());

        verify(mockTagService).save(any(TagDto.class));
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void delete() {
        final Response response = resource.delete(anyLong());

        verify(mockTagService).delete(anyLong());
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void update() throws FlashCardsException
    {
        when(mockTagService.findOne(any(Long.class))).thenReturn(mockTagDto);
        when(mockTagService.save(any(TagDto.class))).thenReturn(mockTagDto);

        final Response response = resource.update(uuid, mockTagDto);

        verify(mockTagService).findOne(any(Long.class));
        verify(mockTagService).save(any(TagDto.class));
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }
}
