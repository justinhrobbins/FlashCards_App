
package org.robbins.flashcards.jersey.filters;

import org.robbins.flashcards.webservices.base.AbstractSecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Component("jerseySecurityFilter")
public class SecurityFilter extends AbstractSecurityFilter implements
        ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        LOGGER.trace("SecurityFilter");

        configureLoggedInUser();
    }
}
