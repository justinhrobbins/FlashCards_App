package org.robbins.flashcards.model.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomJsonDateSerializer extends JsonSerializer<Date> {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	@Override
	public void serialize(Date date, JsonGenerator gen,
			SerializerProvider provider) throws IOException {

		String formattedDate = DATE_FORMAT.format(date);
		gen.writeString(formattedDate);
	}
}
