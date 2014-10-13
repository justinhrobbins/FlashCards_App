
package org.robbins.flashcards.cxf.filters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.repository.facade.UserFacade;
import org.robbins.flashcards.model.User;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class SecurityFilterUT extends BaseMockingTest {

    @Mock
    private User loggedInUser;

    @Mock
    private UserFacade userFacade;

    @Mock
    private Authentication mockAuthentication;

    private SecurityFilter securityFilter;

    @Before
    public void before() {
        securityFilter = new SecurityFilter();
        ReflectionTestUtils.setField(securityFilter, "loggedInUser", loggedInUser);
        ReflectionTestUtils.setField(securityFilter, "userFacade", userFacade);

        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
    }

    @After
    public void detachSubject() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void handleRequest() throws ServiceException {
        org.springframework.security.core.userdetails.User principal = mock(org.springframework.security.core.userdetails.User.class);
        String mockOpenId = new String("open_id");
        UserDto mockUserDto = new UserDto(1L);

        when(mockAuthentication.getPrincipal()).thenReturn(principal);
        when(principal.getUsername()).thenReturn(mockOpenId);
        when(userFacade.findUserByOpenid(mockOpenId)).thenReturn(mockUserDto);

        securityFilter.handleRequest(null, null);

        verify(mockAuthentication).getPrincipal();
        verify(principal).getUsername();
        verify(userFacade).findUserByOpenid(mockOpenId);
    }
}
