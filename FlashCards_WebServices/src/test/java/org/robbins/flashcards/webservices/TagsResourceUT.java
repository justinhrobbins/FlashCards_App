
package org.robbins.flashcards.webservices;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

@Category(UnitTest.class)
public class TagsResourceUT extends BaseMockingTest {

    @Mock
    private TagFacade mockTagFacade;

    @Mock
    private TagDto mockTagDto;

    private TagsResource resource;

    @Before
    public void before() {
        resource = new TagsResource();
        ReflectionTestUtils.setField(resource, "tagFacade", mockTagFacade);
    }

    @Test
    public void search() throws FlashcardsException
	{
        when(mockTagFacade.findByName(any(String.class))).thenReturn(mockTagDto);

        TagDto result = resource.searchByName(any(String.class));

        verify(mockTagFacade).findByName(any(String.class));
        assertThat(result, is(TagDto.class));
    }

    @Test(expected = GenericWebServiceException.class)
    public void search_NotFound() throws FlashcardsException {
        when(mockTagFacade.findByName(any(String.class))).thenReturn(null);

        resource.searchByName(any(String.class));
    }

    @Test
    public void put() throws FlashcardsException {
        when(mockTagFacade.findOne(any(String.class))).thenReturn(mockTagDto);
        when(mockTagFacade.save(any(TagDto.class))).thenReturn(mockTagDto);

        Response response = resource.put(UUID.randomUUID().toString(), mockTagDto);

        verify(mockTagFacade).save(any(TagDto.class));
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void put_WithCreatedBy() throws FlashcardsException {
        when(mockTagDto.getCreatedBy()).thenReturn(new UserDto());
        when(mockTagFacade.save(any(TagDto.class))).thenReturn(mockTagDto);

        Response response = resource.put(UUID.randomUUID().toString(), mockTagDto);

        verify(mockTagFacade).save(any(TagDto.class));
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }
}
