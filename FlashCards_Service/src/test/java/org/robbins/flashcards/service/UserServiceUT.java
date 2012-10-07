package org.robbins.flashcards.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.springdata.UserRepository;
import org.robbins.flashcards.service.springdata.UserServiceImpl;
import org.springframework.test.util.ReflectionTestUtils;

public class UserServiceUT extends BaseTestCase {

	@Mock UserRepository repository;
	UserServiceImpl userService;
	
	@Before
	public void before() {
		userService = new UserServiceImpl();
		ReflectionTestUtils.setField(userService, "repository", repository);		
	}
	
	@Test
	public void testFindUserByOpenid() {
		when(repository.findUserByOpenid("open_id")).thenReturn(new User());
		
		User user = userService.findUserByOpenid("open_id");
		
		Mockito.verify(repository, Mockito.times(1)).findUserByOpenid("open_id");
		assertThat(user, is(User.class));
	}
}