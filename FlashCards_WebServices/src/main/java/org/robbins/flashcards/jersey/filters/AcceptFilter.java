package org.robbins.flashcards.jersey.filters;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

/**
 * Overrides the 'accept' HTTP header with value parsed from the querystring 'accept' parameter.
 * The 'accept' parameter must match 
 * @author Justin Robbins
 *
 */
@Component("jerseyAcceptFilter")
public class AcceptFilter implements ContainerRequestFilter {

	static final Logger logger = LoggerFactory.getLogger(AcceptFilter.class);

	private static String ACCEPT = "accept";
	
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		logger.debug("AcceptFilter");

		if (request.getQueryParameters().containsKey(ACCEPT)) {
			List<String> queryparms = request.getQueryParameters().get(ACCEPT); 
			logger.debug(queryparms.toString());
			
			// does the 'accept' header match either json or xml? 
			if ( (!queryparms.contains(MediaType.APPLICATION_JSON)) && (!queryparms.contains(MediaType.APPLICATION_XML)) ) {
				throw new GenericWebServiceException(
						Response.Status.BAD_REQUEST, "'accept' parameter must container either " + MediaType.APPLICATION_JSON + " or " + MediaType.APPLICATION_XML);
			}
			
			MultivaluedMap<String, String> headers = request.getRequestHeaders();
			headers.put(HttpHeaders.ACCEPT, queryparms);
		}
		
		return request;
	}
}