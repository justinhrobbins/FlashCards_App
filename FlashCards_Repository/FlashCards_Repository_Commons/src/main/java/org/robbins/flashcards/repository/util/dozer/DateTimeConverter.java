
package org.robbins.flashcards.repository.util.dozer;

import java.time.LocalDateTime;
import java.util.Date;

import org.dozer.CustomConverter;
import org.robbins.flashcards.util.DateUtils;
import org.springframework.data.mapping.model.MappingException;

public class DateTimeConverter implements CustomConverter {

    @SuppressWarnings("rawtypes")
    @Override
    public Object convert(final Object existingDestinationFieldValue,
            final Object sourceFieldValue, final Class destinationClass,
            final Class sourceClass) {

        if (sourceFieldValue == null) {
            return null;
        }

        if (sourceFieldValue instanceof Date) {
            // Note that DateTime is immutable, so
            // we can't do much with the existingDestinationFieldValue.
            return DateUtils.asLocalDateTime((Date)sourceFieldValue);
        } else if (sourceFieldValue instanceof LocalDateTime) {
            return DateUtils.asDate((LocalDateTime) sourceFieldValue);
        }

        throw new MappingException("Misconfigured/unsupported mapping");
    }
}
