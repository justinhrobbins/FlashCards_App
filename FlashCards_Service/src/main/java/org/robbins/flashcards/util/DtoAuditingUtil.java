package org.robbins.flashcards.util;


import java.util.Date;

import org.robbins.flashcards.dto.AbstractAuditableDto;

public class DtoAuditingUtil
{

    public static void configureCreatedByAndTime(final AbstractAuditableDto dto, final Long auditingUserId) {
        dto.setCreatedBy(auditingUserId);
        dto.setCreatedDate(new Date());
    }

}
