
package org.robbins.flashcards.webservices.base;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.service.UserService;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AbstractSecurityFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSecurityFilter.class);

    @Inject
    private UserDto loggedInUser;

    @Inject
    private UserService userService;

    /**
     * Gets the logged in user.
     *
     * @return the logged in user
     */
    public UserDto getLoggedInUser() {
        return loggedInUser;
    }

    /*
     * Get the authenticated UserId and add it to the Request scoped bean This bean will
     * be available to the Spring Data auditing.
     */
    protected void configureLoggedInUser() {
        // get the logged in user name from Security Context

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if ("anonymousUser".equals(authentication.getName())) {
            LOGGER.debug("AbstractSecurityFilter - anonymous user");
            return;
        }

        String openId = findOpenId(authentication.getPrincipal());

        try {
            UserDto user = userService.findUserByOpenid(openId);

            if (user != null) {
                // set the user id on the autowired loggedInUser
                getLoggedInUser().setId(user.getId());
                LOGGER.trace("AbstractSecurityFilter - Logged In User Id: "
                        + getLoggedInUser().getId());
            }
        }
        catch (FlashCardsException e)
        {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    private String findOpenId(Object principal)
    {
        if (principal instanceof org.springframework.security.core.userdetails.User)
        {
            return ((org.springframework.security.core.userdetails.User)(principal)).getUsername();
        }
        else if (principal instanceof String) {
            return (String)principal;
        }
        else {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, "Unable to determine principal");
        }
    }
}
