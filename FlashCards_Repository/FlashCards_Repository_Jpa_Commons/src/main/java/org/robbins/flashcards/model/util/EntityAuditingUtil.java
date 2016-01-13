package org.robbins.flashcards.model.util;


import java.time.LocalDateTime;

import org.robbins.flashcards.model.common.AbstractAuditable;

public class EntityAuditingUtil
{

    public static void configureCreatedByAndTime(final AbstractAuditable entity, final Long auditingUserId) {
        entity.setCreatedBy(auditingUserId);
        entity.setCreatedDate(LocalDateTime.now());
    }

}
