package org.robbins.flashcards.webservices;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public abstract class AbstractResource {
	
	static Logger logger = Logger.getLogger(AbstractResource.class);

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
	
	protected Sort getSort(String sort, String order) {
	    Direction direction;		
		if ((StringUtils.isEmpty(order)) || (order.equals("asc"))) {
			direction = Direction.ASC;
		} else if (order.equals("desc")) {
			direction = Direction.DESC;
		}
		else {
			throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
					"Sort order must be 'asc' or 'desc'.  '" + order + "' is not an acceptable sort order");
		}
		
		return new Sort(direction, sort);
	}

	@PostConstruct
	public void postConstruct() throws Exception {
		logger.debug("Entering @PostConstruct");
		configureLoggedInUser();
	}
 
	@PreDestroy
	public void preDestroy() throws Exception {
		logger.debug("Entering preDestroy()");
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
			logger.debug("Logged in userId: " + getLoggedInUser().getId());
		}
	}
}
