package org.robbins.flashcards.webservices.cxf.providers;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.apache.log4j.Logger;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityFilter")
public class SecurityFilter implements RequestHandler {

	private static Logger logger = Logger.getLogger(SecurityFilter.class);

	@Inject
	private User loggedInUser;
	
	@Inject UserService userService;

	/**
	 * Gets the logged in user.
	 *
	 * @return the logged in user
	 */
	public User getLoggedInUser() {
		return loggedInUser;
	}
	
	@Override
	public Response handleRequest(Message inputMessage, ClassResourceInfo resourceClass) {

		logger.debug("SecurityFilter");
		
		configureLoggedInUser();
		
		return null;
	}
	
	/*
	 * Get the authenticated UserId and add it to the Request scoped bean
	 * This bean will be available to the Spring Data auditing.
	 */
	private void configureLoggedInUser() {
		// get the logged in user name from Security Context
//		Long userId = (Long) SecurityUtils.getSubject().getPrincipal();
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		String openId = principal.getUsername();
  	
		User user = userService.findUserByOpenid(openId);
		
		if (user != null) {
	    	// set the user id on the autowired loggedInUser
			getLoggedInUser().setId(user.getId());
			logger.debug("Logged In User Id: " + getLoggedInUser().getId());
		}
	}
}