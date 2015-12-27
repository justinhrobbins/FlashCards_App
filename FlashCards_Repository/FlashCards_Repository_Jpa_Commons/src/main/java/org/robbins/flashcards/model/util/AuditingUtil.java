package org.robbins.flashcards.model.util;


import org.joda.time.DateTime;
import org.robbins.flashcards.model.common.AbstractAuditable;

public class AuditingUtil {

    public static void configureCreatedByAndTime(final AbstractAuditable entity, final Long auditingUserId) {
        entity.setCreatedBy(auditingUserId);
        entity.setCreatedDate(new DateTime());
    }
}
