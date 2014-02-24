
package org.robbins.flashcards.service.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.robbins.flashcards.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DtoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DtoUtil.class);

    private DtoUtil() {
    };

    public static void filterFields(Object obj, Set<String> fields)
            throws ServiceException {
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }

        // convert from Set to Map
        Map<String, String> fieldsMap = fieldsAsMap(fields);

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());

            // loop through the properties of the bean
            for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
                String propertyName = propertyDesc.getName();

                // if (propertyDesc.getPropertyType().equals(Collection.class)) {
                // Method getter = propertyDesc.getReadMethod();
                // filterFields(getter.invoke(obj), fields);
                // }

                // is the current property a collection?
                Method getter = propertyDesc.getReadMethod();
                Object values = getter.invoke(obj);
                if (values instanceof Collection<?>) {
                    // run the filter over the fields in the collection
                    for (Object value : (Collection<?>) values) {
                        filterFields(value, fields);
                    }
                }

                // if we don't explicitly want this field
                if (fieldsMap.get(propertyName) == null) {
                    Method setter = propertyDesc.getWriteMethod();

                    // if the field has a setter
                    if (setter != null) {
                        // set the value of the field to null
                        setter.invoke(obj, new Object[] { null });
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private static Map<String, String> fieldsAsMap(Set<String> fields) {
        Map<String, String> fieldsMap = new HashMap<String, String>();

        for (String field : fields) {
            fieldsMap.put(field, field);
        }

        return fieldsMap;
    }
}
