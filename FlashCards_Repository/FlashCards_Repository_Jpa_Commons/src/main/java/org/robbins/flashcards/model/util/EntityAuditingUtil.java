package org.robbins.flashcards.model.util;


import org.robbins.flashcards.model.common.AbstractAuditable;

import java.time.LocalDateTime;

public class EntityAuditingUtil
{
    public static void configureCreatedByAndTime(final AbstractAuditable entity, final Long auditingUserId) {
        if (entity.isNew()) {
            entity.setCreatedBy(auditingUserId);
            entity.setCreatedDate(LocalDateTime.now());
        }
    }
}
