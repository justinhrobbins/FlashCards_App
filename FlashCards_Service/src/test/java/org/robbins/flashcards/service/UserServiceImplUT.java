
package org.robbins.flashcards.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class UserServiceImplUT extends BaseMockingTest {

    @Mock
    private UserFacade facade;

    private UserServiceImpl userService;

    @Before
    public void before() {
        userService = new UserServiceImpl();
        ReflectionTestUtils.setField(userService, "facade", facade);
    }

    @Test
    public void testFindUserByOpenid() throws FlashcardsException {
        when(facade.findUserByOpenid(Matchers.anyString())).thenReturn(new UserDto());

		UserDto user = userService.findUserByOpenid("open_id");

        Mockito.verify(facade, Mockito.times(1)).findUserByOpenid("open_id");
        assertThat(user, is(UserDto.class));
    }
}
