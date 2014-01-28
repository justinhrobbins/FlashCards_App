
package org.robbins.flashcards.facade.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.UserService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class DefaultUserFacadeUT extends BaseMockingTest {

    @Mock
    private UserService mockService;

    @Mock
    private Mapper mockMapper;

    @Mock
    private User mockUser;

    @Mock
    private UserDto mockUserDto;

    private UserFacade userFacade;

    @Before
    public void before() {
        userFacade = new DefaultUserFacade();
        ReflectionTestUtils.setField(userFacade, "service", mockService);
        ReflectionTestUtils.setField(userFacade, "mapper", mockMapper);
    }

    @Test
    public void findUserByOpenid() {
        when(mockService.findUserByOpenid(any(String.class))).thenReturn(mockUser);
        when(mockMapper.map(mockUser, UserDto.class)).thenReturn(mockUserDto);

        UserDto result = userFacade.findUserByOpenid(any(String.class));

        verify(mockService).findUserByOpenid(any(String.class));
        assertThat(result, is(UserDto.class));
    }

    @Test
    public void findByName_ReturnNull() {
        when(mockService.findUserByOpenid(any(String.class))).thenReturn(null);

        UserDto result = userFacade.findUserByOpenid(any(String.class));

        verify(mockService).findUserByOpenid(any(String.class));
        assertThat(result, is(nullValue()));
    }

    @Test
    public void getDtos() throws ServiceException {
        List<User> mockUserList = Arrays.asList(mockUser);

        List<UserDto> results = userFacade.getDtos(mockUserList, null);

        assertThat(results, is(List.class));
    }

    @Test
    public void getEntities() throws ServiceException {
        List<UserDto> mockUserDtoList = Arrays.asList(mockUserDto);

        List<User> results = userFacade.getEtnties(mockUserDtoList);

        assertThat(results, is(List.class));
    }

    @Test
    public void save() throws ServiceException {
        when(mockService.save(any(User.class))).thenReturn(mockUser);
        when(mockService.findOne(any(Long.class))).thenReturn(mockUser);
        when(mockMapper.map(mockUser, UserDto.class)).thenReturn(mockUserDto);
        when(mockMapper.map(mockUserDto, User.class)).thenReturn(mockUser);

        UserDto result = userFacade.save(mockUserDto);

        verify(mockService).save(any(User.class));
        assertThat(result, is(UserDto.class));
    }

}
