
package org.robbins.flashcards.repository.facade.impl;

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
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;
import org.robbins.flashcards.repository.conversion.DtoConverter;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class DefaultUserFacadeUT extends BaseMockingTest {

    @Mock
    private UserRepository repository;

    @Mock
    private DtoConverter<UserDto, User> converter;

    @Mock
    private User mockUser;

    @Mock
    private UserDto mockUserDto;

    private UserFacade userFacade;

    @Before
    public void before() {
        userFacade = new DefaultUserRepositoryFacade();
        ReflectionTestUtils.setField(userFacade, "repository", repository);
        ReflectionTestUtils.setField(userFacade, "converter", converter);
    }

    @Test
    public void findUserByOpenid() throws FlashcardsException
	{
        when(repository.findUserByOpenid(any(String.class))).thenReturn(mockUser);
        when(converter.getDto(mockUser, null)).thenReturn(mockUserDto);

        UserDto result = userFacade.findUserByOpenid(any(String.class));

        verify(repository).findUserByOpenid(any(String.class));
        assertThat(result, is(UserDto.class));
    }

    @Test
    public void findByName_ReturnNull() throws FlashcardsException {
        when(repository.findUserByOpenid(any(String.class))).thenReturn(null);

        UserDto result = userFacade.findUserByOpenid(any(String.class));

        verify(repository).findUserByOpenid(any(String.class));
        assertThat(result, is(nullValue()));
    }

    @Test
    public void save() throws FlashcardsException {
        when(repository.save(any(User.class))).thenReturn(mockUser);
        when(repository.findOne(any(Long.class))).thenReturn(mockUser);
        when(converter.getDto(mockUser, null)).thenReturn(mockUserDto);
        when(converter.getEntity(mockUserDto)).thenReturn(mockUser);

        UserDto result = userFacade.save(mockUserDto);

        verify(repository).save(any(User.class));
        assertThat(result, is(UserDto.class));
    }

}
