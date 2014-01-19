
package org.robbins.flashcards.auditing;

import javax.inject.Inject;

import org.robbins.flashcards.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.AuditorAware;

public class AuditingAwareUser implements AuditorAware<User> {

    static final Logger logger = LoggerFactory.getLogger(AuditingAwareUser.class);

    @Inject
    private ApplicationContext context;

    @Override
    public User getCurrentAuditor() {
        User auditor = new User(((User) context.getBean("loggedInUser")).getId());

        logger.debug("Logged In User Id: " + auditor.getId());

        return auditor;
    }
}
