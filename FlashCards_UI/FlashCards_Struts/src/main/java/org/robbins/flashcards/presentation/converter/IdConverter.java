
package org.robbins.flashcards.presentation.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

public class IdConverter extends StrutsTypeConverter {

    @Override
    public Object convertFromString(@SuppressWarnings("rawtypes") final Map context,
            final String[] values, @SuppressWarnings("rawtypes") final Class toClass) {
        if ((values != null) && (values.length > 0) && (!values[0].equals("null"))) {
            try {
                return Long.parseLong(values[0]);
            } catch (NumberFormatException e) {
                throw new TypeConversionException(e);
            }
        }
        return null;
    }

    @Override
    public String convertToString(@SuppressWarnings("rawtypes") final Map context,
            final Object o) {
        return String.valueOf(o);
    }
}
