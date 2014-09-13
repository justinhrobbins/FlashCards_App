package org.robbins.flashcards.conversion;


import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robbins.flashcards.conversion.impl.DefaultUserDtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.model.User;
import org.robbins.tests.BaseMockingTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DefaultUserDtoConverterUT extends BaseMockingTest {
    private DtoConverter converter;
    private User user;
    private UserDto userDto;
    private final String OPEN_ID = "open id";

    @Mock
    private Mapper mockMapper;

    @Before
    public void setup()
    {
        converter = new DefaultUserDtoConverter();
        ReflectionTestUtils.setField(converter, "mapper", mockMapper);

        user = new User();
        user.setOpenid(OPEN_ID);

        userDto = new UserDto();
        userDto.setOpenid(OPEN_ID);
    }

    @Test
    public void getDtos() throws ServiceException {
        List<User> mockUserList = Arrays.asList(user);
        when(mockMapper.map(user, UserDto.class)).thenReturn(userDto);

        List<UserDto> results = converter.getDtos(mockUserList, null);

        assertThat(results, is(List.class));
        verify(mockMapper).map(user, UserDto.class);
    }

    @Test
    public void getEntities() throws ServiceException {
        List<UserDto> mockUserDtoList = Arrays.asList(userDto);
        when(mockMapper.map(userDto, User.class)).thenReturn(user);

        List<User> results = converter.getEtnties(mockUserDtoList);

        assertThat(results, is(List.class));
        verify(mockMapper).map(userDto, User.class);
    }
}
