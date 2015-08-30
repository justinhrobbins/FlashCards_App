
package org.robbins.flashcards.repository.util;

import org.apache.commons.collections.CollectionUtils;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class DtoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DtoUtil.class);

    private DtoUtil() {
    }

    public static void filterFields(final Object obj, final Set<String> fields)
            throws RepositoryException {
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }

        // convert from Set to Map
        final Map<String, String> fieldsMap = fieldsAsMap(fields);

        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(obj.getClass());
        } catch (IntrospectionException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException(e.getMessage(), e);
        }

        Arrays.stream(beanInfo.getPropertyDescriptors()).forEach(propertyDesc -> applyFilterToPropertyDescriptor(fields, fieldsMap, obj, propertyDesc));
    }

    private static void applyFilterToPropertyDescriptor(final Set<String> fields, final Map<String, String> fieldsMap, final Object obj, final PropertyDescriptor propertyDesc) {
        final String propertyName = propertyDesc.getName();

        // is the current property a collection?
        final Method getter = propertyDesc.getReadMethod();
        final Object values;
        try {
            values = getter.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException(e.getMessage(), e);
        }
        if (values instanceof Collection<?>) {
            // run the filter over the fields in the collection
            ((Collection<?>) values).forEach(value ->
                            filterFields(value, fields)
            );
        }

        // if we don't explicitly want this field
        if (fieldsMap.get(propertyName) == null) {
            Method setter = propertyDesc.getWriteMethod();

            // if the field has a setter
            if (setter != null) {
                // set the value of the field to null
                try {
                    setter.invoke(obj, new Object[]{null});
                } catch (IllegalAccessException | InvocationTargetException e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new RepositoryException(e.getMessage(), e);
                }
            }
        }
    }

    private static Map<String, String> fieldsAsMap(final Set<String> fields) {

        return fields.stream().collect(
                Collectors.toMap(field -> field, field -> field));
    }
}
