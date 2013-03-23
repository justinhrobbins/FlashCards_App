package org.robbins.flashcards.service.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.jpa.UserRepository;
import org.robbins.flashcards.service.jpa.UserServiceImpl;
import org.robbins.tests.BaseTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class UserServiceUT extends BaseTest {

	@Mock UserRepository repository;
	UserServiceImpl userService;
	
	@Before
	public void before() {
		userService = new UserServiceImpl();
		ReflectionTestUtils.setField(userService, "repository", repository);		
	}
	
	@Test
	public void testFindUserByOpenid() {
		when(repository.findUserByOpenid(Mockito.anyString())).thenReturn(new User());
		
		User user = userService.findUserByOpenid("open_id");
		
		Mockito.verify(repository, Mockito.times(1)).findUserByOpenid("open_id");
		assertThat(user, is(User.class));
	}
}