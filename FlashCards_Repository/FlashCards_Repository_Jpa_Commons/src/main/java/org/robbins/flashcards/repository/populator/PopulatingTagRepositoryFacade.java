
package org.robbins.flashcards.repository.populator;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.model.util.EntityAuditingUtil;
import org.robbins.flashcards.repository.facade.impl.DefaultTagRepositoryFacade;
import org.springframework.stereotype.Component;

@Component("populatingTagRepositoryFacade")
public class PopulatingTagRepositoryFacade extends DefaultTagRepositoryFacade {

    @Override
    public void configureCreatedByAndTime(Tag entity) {
        EntityAuditingUtil.configureCreatedByAndTime(entity, 1L);
    }
}
