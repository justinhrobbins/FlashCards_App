
package org.robbins.flashcards.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        Hibernate4Module hm = new Hibernate4Module();
        hm.disable(Feature.FORCE_LAZY_LOADING);
        this.registerModule(hm);

        this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.setFailOnUnknownId(false);
        this.setFilters(filterProvider);
    }

    public void setPrettyPrint(final boolean prettyPrint) {
        configure(SerializationFeature.INDENT_OUTPUT, prettyPrint);
    }
}
