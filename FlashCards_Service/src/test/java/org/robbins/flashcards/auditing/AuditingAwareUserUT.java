package org.robbins.flashcards.auditing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.model.User;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class AuditingAwareUserUT extends BaseMockingTest {

	AuditingAwareUser auditingAwareUser;
	
	@Mock
	User mockUser;
	
	@Mock
	private ApplicationContext mockContext;
	
	@Before
	public void before() {
		auditingAwareUser = new AuditingAwareUser();
		ReflectionTestUtils.setField(auditingAwareUser, "context", mockContext);
	}
	
	@Test
	public void getCurrentAuditor() {
		when(mockContext.getBean("loggedInUser")).thenReturn(mockUser);
		when(mockUser.getId()).thenReturn(1L);
		
		User result = auditingAwareUser.getCurrentAuditor();
		
		verify(mockContext).getBean("loggedInUser");
		assertThat(result, is(User.class));
		assertThat(result.getId(), is(1L));
	}
}
