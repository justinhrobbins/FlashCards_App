package org.robbins.flashcards.repository.conversion;


import java.util.Arrays;
import java.util.List;

import org.dozer.Mapper;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.conversion.impl.DefaultUserDtoConverter;
import org.robbins.tests.BaseMockingTest;
import org.springframework.test.util.ReflectionTestUtils;

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
    public void getDtos() throws RepositoryException
	{
        List<User> mockUserList = Arrays.asList(user);
        Mockito.when(mockMapper.map(user, UserDto.class)).thenReturn(userDto);

        List<UserDto> results = converter.getDtos(mockUserList, null);

        Assert.assertThat(results, CoreMatchers.is(List.class));
        Mockito.verify(mockMapper).map(user, UserDto.class);
    }

    @Test
    public void getEntities() throws ServiceException {
        List<UserDto> mockUserDtoList = Arrays.asList(userDto);
        Mockito.when(mockMapper.map(userDto, User.class)).thenReturn(user);

        List<User> results = converter.getEtnties(mockUserDtoList);

        Assert.assertThat(results, CoreMatchers.is(List.class));
        Mockito.verify(mockMapper).map(userDto, User.class);
    }
}
