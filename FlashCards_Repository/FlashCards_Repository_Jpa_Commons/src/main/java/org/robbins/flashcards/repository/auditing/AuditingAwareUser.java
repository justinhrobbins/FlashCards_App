
package org.robbins.flashcards.repository.auditing;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.AuditorAware;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AuditingAwareUser implements AuditorAware<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditingAwareUser.class);

    @Inject
    private ApplicationContext context;

    @PersistenceContext
    private EntityManager em;

    @Override
    public User getCurrentAuditor() {
        UserDto userDto = new UserDto(((UserDto) context.getBean("loggedInUser")).getId());
        User auditor = em.find(User.class, userDto.getId());

        LOGGER.debug("Logged In User Id: " + auditor.getId());

        return auditor;
    }
}
