
package org.robbins.flashcards.repository.populator;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.util.EntityAuditingUtil;
import org.robbins.flashcards.repository.facade.impl.DefaultFlashCardRepositoryFacade;
import org.springframework.stereotype.Component;

@Component("populatingFlashcardRepositoryFacade")
public class PopulatingFlashCardRepositoryFacade extends DefaultFlashCardRepositoryFacade
{
    @Override
    public void configureCreatedByAndTime(FlashCard entity) {
        EntityAuditingUtil.configureCreatedByAndTime(entity, 1L);
    }
}
