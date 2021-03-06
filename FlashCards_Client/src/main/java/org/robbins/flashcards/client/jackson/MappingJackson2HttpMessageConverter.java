
package org.robbins.flashcards.client.jackson;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;

public class MappingJackson2HttpMessageConverter extends
        AbstractHttpMessageConverter<Object> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private ObjectMapper objectMapper = new ObjectMapper();

    private boolean prefixJson = false;

    public MappingJackson2HttpMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    public void setObjectMapper(final ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "ObjectMapper must not be null");
        this.objectMapper = objectMapper;
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    public void setPrefixJson(final boolean prefixJson) {
        this.prefixJson = prefixJson;
    }

    @Override
    public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
        JavaType javaType = getJavaType(clazz);
        return (this.objectMapper.canDeserialize(javaType) && canRead(mediaType));
    }

    @Override
    public boolean canWrite(final Class<?> clazz, final MediaType mediaType) {
        return (this.objectMapper.canSerialize(clazz) && canWrite(mediaType));
    }

    @Override
    protected boolean supports(final Class<?> clazz) {
        // should not be called, since we override canRead/Write instead
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object readInternal(final Class<?> clazz,
            final HttpInputMessage inputMessage)
            throws IOException {

        JavaType javaType = getJavaType(clazz);
        try {
            return this.objectMapper.readValue(inputMessage.getBody(), javaType);
        } catch (JsonProcessingException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: "
                    + ex.getMessage(), ex);
        }
    }

    @Override
    protected void writeInternal(final Object object,
            final HttpOutputMessage outputMessage)
            throws IOException {

        final JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
        final JsonGenerator jsonGenerator = this.objectMapper.getJsonFactory().createJsonGenerator(
                outputMessage.getBody(), encoding);
        try {
            if (this.prefixJson) {
                jsonGenerator.writeRaw("{} && ");
            }
            this.objectMapper.writeValue(jsonGenerator, object);
        } catch (JsonProcessingException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: "
                    + ex.getMessage(), ex);
        }
    }

    protected JavaType getJavaType(final Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }

    protected JsonEncoding getJsonEncoding(final MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            Charset charset = contentType.getCharSet();

            Optional<JsonEncoding> encoding = Arrays.stream(JsonEncoding.values())
                    .filter(value ->
                            charset.name().equals(value.getJavaName()))
                    .findFirst();

            return encoding.isPresent() ? encoding.get() : null;
        }
        return JsonEncoding.UTF8;
    }
}
