package org.robbins.flashcards.webservices;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.model.User;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class UsersResourceUT extends BaseMockingTest {

	@Mock
	UserFacade userFacade;
	
	@Mock
	User user;
	
	UsersResource resource;
	
	@Before
	public void before() {
		resource = new UsersResource();
		ReflectionTestUtils.setField(resource, "userFacade", userFacade);
	}
	
	@Test
	public void search() {
		when(userFacade.findUserByOpenid(any(String.class))).thenReturn(user);
		
		User result = resource.search(any(String.class));
		
		verify(userFacade).findUserByOpenid(any(String.class));
		assertThat(result, is(User.class));
	}
	
	@Test
	public void put() {
		when(userFacade.findOne(any(Long.class))).thenReturn(user);
		when(userFacade.save(any(User.class))).thenReturn(user);
		
		Response response = resource.put(1L, user);
		
		verify(userFacade).findOne(any(Long.class));
		verify(userFacade).save(any(User.class));
		assertThat(response.getStatus(), is(204));
	}
}
