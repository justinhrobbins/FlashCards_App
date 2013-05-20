package org.robbins.flashcards.webservices.cxf.providers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.UserService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class SecurityFilterUT extends BaseMockingTest {

	@Mock
	User loggedInUser;

	@Mock
	UserService userService;
	
	@Mock
	Authentication mockAuthentication;

	SecurityFilter securityFilter;

	@Before
	public void before() {
		securityFilter = new SecurityFilter();
		ReflectionTestUtils.setField(securityFilter, "loggedInUser", loggedInUser);
		ReflectionTestUtils.setField(securityFilter, "userService", userService);

		SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
	}

	@After
	public void detachSubject() {
		SecurityContextHolder.clearContext();
	}
	
	@Test
	public void handleRequest() {
		org.springframework.security.core.userdetails.User principal = mock(org.springframework.security.core.userdetails.User.class);
		String mockOpenId = new String("open_id");
		User mockUser = new User(1L);
		
		when(mockAuthentication.getPrincipal()).thenReturn(principal);
		when(principal.getUsername()).thenReturn(mockOpenId);
		when(userService.findUserByOpenid(mockOpenId)).thenReturn(mockUser);

		securityFilter.handleRequest(null, null);
		
		verify(mockAuthentication).getPrincipal();
		verify(principal).getUsername();
		verify(userService).findUserByOpenid(mockOpenId);
	}
}
