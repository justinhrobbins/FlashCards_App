package org.robbins.flashcards.webservices.base;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.jersey.filters.SecurityFilter;
import org.robbins.flashcards.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AbstractSecurityFilter {
	
	private static Logger logger = Logger.getLogger(SecurityFilter.class);
	
	@Inject
	private User loggedInUser;
	
	@Inject 
	private UserFacade userFacade;
	
	/**
	 * Gets the logged in user.
	 *
	 * @return the logged in user
	 */
	public User getLoggedInUser() {
		return loggedInUser;
	}
	
	
	/*
	 * Get the authenticated UserId and add it to the Request scoped bean
	 * This bean will be available to the Spring Data auditing.
	 */
	protected void configureLoggedInUser() {
		// get the logged in user name from Security Context
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		
		if ("anonymousUser".equals(authentication.getName())) {
			logger.debug("AbstractSecurityFilter - anonymous user");
			return;
		}

		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		String openId = principal.getUsername();
  	
		UserDto user = userFacade.findUserByOpenid(openId);
		
		if (user != null) {
	    	// set the user id on the autowired loggedInUser
			getLoggedInUser().setId(user.getId());
			logger.debug("AbstractSecurityFilter - Logged In User Id: " + getLoggedInUser().getId());
		}
	}
}
