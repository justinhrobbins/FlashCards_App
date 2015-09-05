
package org.robbins.flashcards.repository.auditing;

import org.robbins.flashcards.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.AuditorAware;

import javax.inject.Inject;

public class AuditingAwareUser implements AuditorAware<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditingAwareUser.class);

    @Inject
    private ApplicationContext context;

    @Override
    public String getCurrentAuditor() {
        String auditor = ((UserDto) context.getBean("loggedInUser")).getId();

        LOGGER.trace("Logged In User Id: " + auditor);

        return auditor;
    }
}
