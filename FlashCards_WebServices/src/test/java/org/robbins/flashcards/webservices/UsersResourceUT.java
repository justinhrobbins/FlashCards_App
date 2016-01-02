
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
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.service.UserService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class UsersResourceUT extends BaseMockingTest {

    @Mock
    private UserService mockUserService;

    @Mock
    private UserDto mockUserDto;

    private UsersResource resource;

    @Before
    public void before() {
        resource = new UsersResource();
        ReflectionTestUtils.setField(resource, "userService", mockUserService);
    }

    @Test
    public void search() throws FlashCardsException
	{
        when(mockUserService.findUserByOpenid(any(String.class))).thenReturn(mockUserDto);

        UserDto result = resource.search(any(String.class));

        verify(mockUserService).findUserByOpenid(any(String.class));
        assertThat(result, is(UserDto.class));
    }

    @Test
    public void put() throws FlashCardsException
    {
        when(mockUserService.findOne(any(Long.class))).thenReturn(mockUserDto);
        when(mockUserService.save(any(UserDto.class))).thenReturn(mockUserDto);

        Response response = resource.put(RandomUtils.nextLong(0L, Long.MAX_VALUE), mockUserDto);

        verify(mockUserService).save(any(UserDto.class));
        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }
}
