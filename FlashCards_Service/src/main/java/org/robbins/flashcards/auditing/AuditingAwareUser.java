package org.robbins.flashcards.auditing;

import javax.inject.Inject;

import org.robbins.flashcards.model.User;
import org.springframework.data.domain.AuditorAware;


public class AuditingAwareUser implements AuditorAware<User> {

	@Inject
	User loggedInUser;
	
	@Override
	public User getCurrentAuditor() {
		return loggedInUser;
	}
}
