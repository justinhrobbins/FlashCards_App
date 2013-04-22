package org.robbins.flashcards.auditing;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.robbins.flashcards.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.AuditorAware;


public class AuditingAwareUser implements AuditorAware<User> {
	
	private static Logger logger = Logger.getLogger(AuditingAwareUser.class);
	
    @Inject
    private ApplicationContext context;
	
	@Override
	public User getCurrentAuditor() {
        User auditor = new User(((User)context.getBean("loggedInUser")).getId());

        logger.debug("Logged In User Id: " + auditor.getId());

        return auditor;
	}
}
