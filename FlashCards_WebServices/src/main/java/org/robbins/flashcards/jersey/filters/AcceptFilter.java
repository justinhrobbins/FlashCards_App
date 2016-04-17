
package org.robbins.flashcards.jersey.filters;

import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;



/**
 * Overrides the 'accept' HTTP header with value parsed from the querystring 'accept'
 * parameter. The 'accept' parameter must match
 *
 * @author Justin Robbins
 *
 */
@Provider
@Component("jerseyAcceptFilter")
public class AcceptFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptFilter.class);

    private static String ACCEPT = "accept";

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        LOGGER.trace("AcceptFilter");

        final MultivaluedMap<String, String> queryParametersMap = requestContext.getUriInfo().getQueryParameters();

        if (queryParametersMap.containsKey(ACCEPT)) {
            final List<String> queryparmsList = queryParametersMap.get(ACCEPT);
            LOGGER.debug(queryparmsList.toString());

            // does the 'accept' header match either json or xml?
            if ((!queryparmsList.contains(MediaType.APPLICATION_JSON))
                    && (!queryparmsList.contains(MediaType.APPLICATION_XML))) {
                throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
                        "'accept' parameter must container either "
                                + MediaType.APPLICATION_JSON + " or "
                                + MediaType.APPLICATION_XML);
            }

            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
            headers.put(HttpHeaders.ACCEPT, queryparmsList);
        }
    }
}
