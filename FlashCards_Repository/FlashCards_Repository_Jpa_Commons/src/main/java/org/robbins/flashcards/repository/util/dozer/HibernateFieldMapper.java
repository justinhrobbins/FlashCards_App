
package org.robbins.flashcards.repository.util.dozer;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.hibernate.Hibernate;
import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;

public class HibernateFieldMapper implements CustomFieldMapper {

    /**
     * If false is returned from the call to mapField(), then the field will be
     * subsequently mapped by Dozer as normal.
     */
    @Override
    public boolean mapField(final Object source, final Object destination,
            final Object sourceFieldValue, final ClassMap classMap,
            final FieldMap fieldMapping) {
        // Check if field is a Hibernate PersistentCollection
        if (!(sourceFieldValue instanceof PersistentCollection) && !(sourceFieldValue instanceof HibernateProxy)) {
            // Allow dozer to map as normal
            return false;
        }
        else {
            return !Hibernate.isInitialized(sourceFieldValue);
        }
    }
}
