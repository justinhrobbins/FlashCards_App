package org.robbins.flashcards.webservices.filters;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.robbins.flashcards.model.User;
import org.springframework.stereotype.Component;

@Component("securityFilter")
public class SecurityFilter implements RequestHandler {

	private static Logger logger = Logger.getLogger(SecurityFilter.class);

	@Inject
	private User loggedInUser;

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
		Long userId = (Long) SecurityUtils.getSubject().getPrincipal();
  	
		if (userId != null) {
	    	// set the user id on the autowired loggedInUser
			getLoggedInUser().setId(userId);
			logger.debug("Logged In User Id: " + getLoggedInUser().getId());
		}
	}
}