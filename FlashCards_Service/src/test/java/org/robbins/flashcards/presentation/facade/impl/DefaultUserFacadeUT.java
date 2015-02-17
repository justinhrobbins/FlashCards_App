
package org.robbins.flashcards.presentation.facade.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.service.UserService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class DefaultUserFacadeUT extends BaseMockingTest {

    @Mock
    private UserService mockService;

    @Mock
    private UserDto mockUserDto;

    private UserFacade userFacade;

    @Before
    public void before() {
        userFacade = new DefaultUserFacade();
        ReflectionTestUtils.setField(userFacade, "service", mockService);
    }

    @Test
    public void findUserByOpenid() throws FlashcardsException
	{
        when(mockService.findUserByOpenid(any(String.class))).thenReturn(mockUserDto);

        UserDto result = userFacade.findUserByOpenid(any(String.class));

        verify(mockService).findUserByOpenid(any(String.class));
        assertThat(result, is(UserDto.class));
    }

    @Test
    public void findByName_ReturnNull() throws FlashcardsException {
        when(mockService.findUserByOpenid(any(String.class))).thenReturn(null);

        UserDto result = userFacade.findUserByOpenid(any(String.class));

        verify(mockService).findUserByOpenid(any(String.class));
        assertThat(result, is(nullValue()));
    }

    @Test
    public void save() throws FlashcardsException {
        when(mockService.save(any(UserDto.class))).thenReturn(mockUserDto);
        when(mockService.findOne(any(String.class))).thenReturn(mockUserDto);

        UserDto result = userFacade.save(mockUserDto);

        verify(mockService).save(any(UserDto.class));
        assertThat(result, is(UserDto.class));
    }

}
