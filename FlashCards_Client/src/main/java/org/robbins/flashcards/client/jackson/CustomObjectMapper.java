
package org.robbins.flashcards.client.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {

        this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public void setPrettyPrint(final boolean prettyPrint) {
        configure(SerializationFeature.INDENT_OUTPUT, prettyPrint);
    }
}
