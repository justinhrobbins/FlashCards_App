
package org.robbins.flashcards.repository.populator;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.model.util.EntityAuditingUtil;
import org.robbins.flashcards.repository.facade.impl.DefaultUserRepositoryFacade;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("populatingUserRepositoryFacade")
public class PopulatingUserRepositoryFacade extends DefaultUserRepositoryFacade {

    @Override
    public void configureCreatedByAndTime(User entity) {
        EntityAuditingUtil.configureCreatedByAndTime(entity, 1L);
    }
}
