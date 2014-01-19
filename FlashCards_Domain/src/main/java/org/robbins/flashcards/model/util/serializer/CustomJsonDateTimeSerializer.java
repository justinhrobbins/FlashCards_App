
package org.robbins.flashcards.model.util.serializer;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomJsonDateTimeSerializer extends JsonSerializer<DateTime> {

    private static final String DATE_SHORT = "MM/dd/yyyy";

    @Override
    public void serialize(DateTime date, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_SHORT);
        String formattedDate = fmt.print(date);
        gen.writeString(formattedDate);
    }
}
