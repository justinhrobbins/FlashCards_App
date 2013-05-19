package org.robbins.flashcards.webservices.cxf.providers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.webservices.cxf.providers.SecurityFilter;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class SecurityFilterUT extends BaseMockingTest {

	@Mock
	User loggedInUser;

	private ThreadState threadState;
	protected Subject mockSubject;

	SecurityFilter securityFilter;

	@Before
	public void before() {
		securityFilter = new SecurityFilter();
		ReflectionTestUtils.setField(securityFilter, "loggedInUser", loggedInUser);

		mockSubject = Mockito.mock(Subject.class);
		threadState = new SubjectThreadState(mockSubject);
		threadState.bind();
	}

	@After
	public void detachSubject() {
		threadState.clear();
	}

	@Test
	public void handleRequest() {
		Long principal = 1L;
		when(mockSubject.getPrincipal()).thenReturn(principal);

		securityFilter.handleRequest(null, null);
		
		verify(mockSubject).getPrincipal();
	}
}
