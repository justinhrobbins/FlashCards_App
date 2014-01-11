package org.robbins.flashcards.model.util.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomJsonDateSerializer extends JsonSerializer<Date> {

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	@Override
	public void serialize(Date date, JsonGenerator gen,
			SerializerProvider provider) throws IOException {

		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String formattedDate = dateFormat.format(date);
		gen.writeString(formattedDate);
	}
}
