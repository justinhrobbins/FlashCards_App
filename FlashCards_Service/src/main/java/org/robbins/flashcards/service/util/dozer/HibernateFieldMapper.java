
package org.robbins.flashcards.service.util.dozer;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.hibernate.collection.internal.PersistentSet;

public class HibernateFieldMapper implements CustomFieldMapper {

    /**
     * If false is returned from the call to mapField(), then the field will be
     * subsequently mapped by Dozer as normal.
     */
    @Override
    public boolean mapField(final Object source, final Object destination,
            final Object sourceFieldValue, final ClassMap classMap,
            final FieldMap fieldMapping) {
        // Check if field is a Hibernate PersistentSet
        if (!(sourceFieldValue instanceof PersistentSet)) {
            // Allow dozer to map as normal
            return false;
        }

        // Check if field is already initialized
        if (((PersistentSet) sourceFieldValue).wasInitialized()) {
            // Allow dozer to map as normal
            return false;
        }

        // tell dozer that the field is mapped
        return true;
    }
}
