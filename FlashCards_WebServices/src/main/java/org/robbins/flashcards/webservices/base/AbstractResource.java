
package org.robbins.flashcards.webservices.base;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.cxf.common.util.StringUtils;

public abstract class AbstractResource {

    // Convert the vectorized 'fields' parameter to a Set<String>
    protected Set<String> getFieldsAsSet(String fields) {

        if (StringUtils.isEmpty(fields)) {
            return null;
        }

        Set<String> filterProperties = new HashSet<String>();
        StringTokenizer st = new StringTokenizer(fields, ",");
        while (st.hasMoreTokens()) {
            String field = st.nextToken();

            // never allow 'userpassword' to be passed even if it was
            // specifically requested
            if (field.equals("userpassword")) {
                continue;
            }

            // add the field to the Set<String>
            filterProperties.add(field);
        }
        return filterProperties;
    }
}
