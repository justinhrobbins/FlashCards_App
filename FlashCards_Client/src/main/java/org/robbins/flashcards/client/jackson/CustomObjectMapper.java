
package org.robbins.flashcards.client.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {

        this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.registerModule(new JodaModule());
    }

    public void setPrettyPrint(final boolean prettyPrint) {
        configure(SerializationFeature.INDENT_OUTPUT, prettyPrint);
    }
}
