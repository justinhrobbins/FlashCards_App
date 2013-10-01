package org.robbins.flashcards.cxf.filters;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.apache.log4j.Logger;
import org.robbins.flashcards.webservices.base.AbstractSecurityFilter;
import org.springframework.stereotype.Component;

@Component("securityFilter")
public class SecurityFilter extends AbstractSecurityFilter implements RequestHandler {

	private static Logger logger = Logger.getLogger(SecurityFilter.class);
	
	@Override
	public Response handleRequest(Message inputMessage, ClassResourceInfo resourceClass) {

		logger.debug("SecurityFilter");
		
		configureLoggedInUser();
		
		return null;
	}
}