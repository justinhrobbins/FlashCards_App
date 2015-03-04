
package org.robbins.flashcards.repository.util.dozer;

import org.dozer.CustomConverter;
import org.joda.time.DateTime;
import org.springframework.data.mapping.model.MappingException;

import java.util.Date;
import java.util.UUID;

public class UUIDConverter implements CustomConverter {

    @SuppressWarnings("rawtypes")
    @Override
    public Object convert(final Object existingDestinationFieldValue,
            final Object sourceFieldValue, final Class destinationClass,
            final Class sourceClass) {

        if (sourceFieldValue == null) {
            return null;
        }

        if (sourceFieldValue instanceof UUID) {
            return sourceFieldValue.toString();
        } else if (sourceFieldValue instanceof java.lang.String) {
            return UUID.fromString((String) sourceFieldValue);
        }

        throw new MappingException("Misconfigured/unsupported mapping");
    }
}
