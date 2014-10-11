
package org.robbins.flashcards.repository.util.dozer;

import java.util.Date;

import org.dozer.CustomConverter;
import org.joda.time.DateTime;
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
            return new DateTime(sourceFieldValue);
        } else if (sourceFieldValue instanceof DateTime) {
            return ((DateTime) sourceFieldValue).toDate();
        }

        throw new MappingException("Misconfigured/unsupported mapping");
    }
}
