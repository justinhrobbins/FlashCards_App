
package org.robbins.flashcards.webservices.base;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.inject.Inject;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.context.ApplicationContext;

public abstract class AbstractResource {

    @Inject
    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

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
