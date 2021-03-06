
package org.robbins.flashcards.webservices;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.service.TagService;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class TagsResourceUT extends BaseMockingTest {

    @Mock
    private TagService mockTagService;

    @Mock
    private TagDto mockTagDto;

    private TagsResource resource;

    @Before
    public void before() {
        resource = new TagsResource();
        ReflectionTestUtils.setField(resource, "tagService", mockTagService);
    }

    @Test
    public void search() throws FlashCardsException
	{
        when(mockTagService.findByName(any(String.class))).thenReturn(mockTagDto);

        TagDto result = resource.searchByName(any(String.class));

        verify(mockTagService).findByName(any(String.class));
        assertThat(result, is(TagDto.class));
    }

    @Test(expected = GenericWebServiceException.class)
    public void search_NotFound() throws FlashCardsException
    {
        when(mockTagService.findByName(any(String.class))).thenReturn(null);

        resource.searchByName(any(String.class));
    }

    @Test
    public void put() throws FlashCardsException
    {
        when(mockTagService.findOne(any(Long.class))).thenReturn(mockTagDto);
        when(mockTagService.save(any(TagDto.class))).thenReturn(mockTagDto);

        Response response = resource.put(RandomUtils.nextLong(0L, Long.MAX_VALUE), mockTagDto);

        verify(mockTagService).save(any(TagDto.class));
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void put_WithCreatedBy() throws FlashCardsException
    {
        when(mockTagDto.getCreatedBy()).thenReturn(0L);
        when(mockTagService.save(any(TagDto.class))).thenReturn(mockTagDto);

        Response response = resource.put(RandomUtils.nextLong(0L, Long.MAX_VALUE), mockTagDto);

        verify(mockTagService).save(any(TagDto.class));
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }
}
