package org.robbins.flashcards.jersey.filters;

import org.apache.log4j.Logger;
import org.robbins.flashcards.webservices.base.AbstractSecurityFilter;
import org.springframework.stereotype.Component;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Component("jerseySecurityFilter")
public class SecurityFilter extends AbstractSecurityFilter implements ContainerRequestFilter {

	private static Logger logger = Logger.getLogger(SecurityFilter.class);

	@Override
	public ContainerRequest filter(ContainerRequest request) {
		logger.debug("SecurityFilter");
		
		configureLoggedInUser();
		
		return request;
	}
}