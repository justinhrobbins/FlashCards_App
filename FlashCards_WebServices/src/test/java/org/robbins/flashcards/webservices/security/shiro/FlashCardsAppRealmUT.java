package org.robbins.flashcards.webservices.security.shiro;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.UserService;
import org.robbins.tests.BaseMockingTest;
import org.springframework.test.util.ReflectionTestUtils;

public class FlashCardsAppRealmUT extends BaseMockingTest {
	
	@Mock
	UserService mockUserService;

	FlashCardsAppRealm realm;
	
	@Before
	public void before() {
		realm = new FlashCardsAppRealm();
		ReflectionTestUtils.setField(realm, "userService", mockUserService);
	}

	@Test
	public void doGetAuthenticationInfo_withNullUser() {
		UsernamePasswordToken token = new UsernamePasswordToken("user_name", "password");
		when(mockUserService.findUserByOpenid(token.getUsername())).thenReturn(null);

		AuthenticationInfo authInfo = realm.doGetAuthenticationInfo(token);
		
		assertThat(authInfo, is(nullValue()));
	}
	
	@Test
	public void doGetAuthenticationInfo() {
		UsernamePasswordToken token = new UsernamePasswordToken("user_name", "password");
		User mockUser = mock(User.class);
		when(mockUserService.findUserByOpenid(token.getUsername())).thenReturn(mockUser);
		when(mockUser.getId()).thenReturn(new Long(1L));
		
		AuthenticationInfo authInfo = realm.doGetAuthenticationInfo(token);
		assertThat(authInfo, is(AuthenticationInfo.class));
	}
}
