
package org.robbins.flashcards.jersey.filters;

import org.robbins.flashcards.webservices.base.AbstractSecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Component("jerseySecurityFilter")
public class SecurityFilter extends AbstractSecurityFilter implements
        ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);

    @Override
    public ContainerRequest filter(ContainerRequest request) {
        LOGGER.debug("SecurityFilter");

        configureLoggedInUser();

        return request;
    }
}
