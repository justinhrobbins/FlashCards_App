
package org.robbins.flashcards.repository.facade.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.repository.auditing.AuditingAwareUser;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Mock
    private AuditingAwareUser auditorAware;

    private UserFacade userFacade;

    @Before
    public void before() {
        userFacade = new DefaultUserRepositoryFacade();
        ReflectionTestUtils.setField(userFacade, "repository", repository);
        ReflectionTestUtils.setField(userFacade, "converter", converter);
        ReflectionTestUtils.setField(userFacade, "auditorAware", auditorAware);
    }

    @Test
    public void findUserByOpenid() throws FlashCardsException
	{
        when(repository.findUserByOpenid(any(String.class))).thenReturn(mockUser);
        when(converter.getDto(mockUser, null)).thenReturn(mockUserDto);

        UserDto result = userFacade.findUserByOpenid(any(String.class));

        verify(repository).findUserByOpenid(any(String.class));
        assertThat(result, is(UserDto.class));
    }

    @Test
    public void findByName_ReturnNull() throws FlashCardsException
    {
        when(repository.findUserByOpenid(any(String.class))).thenReturn(null);

        UserDto result = userFacade.findUserByOpenid(any(String.class));

        verify(repository).findUserByOpenid(any(String.class));
        assertThat(result, is(nullValue()));
    }

    @Test
    public void save() throws FlashCardsException
    {
        when(repository.save(any(User.class))).thenReturn(mockUser);
        when(repository.findOne(any(String.class))).thenReturn(mockUser);
        when(converter.getDto(mockUser, null)).thenReturn(mockUserDto);
        when(converter.getEntity(mockUserDto)).thenReturn(mockUser);

        UserDto result = userFacade.save(mockUserDto);

        verify(repository).save(any(User.class));
        assertThat(result, is(UserDto.class));
    }

}
