
package org.robbins.flashcards.cxf.filters;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.ext.ResponseHandler;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.jaxrs.model.Parameter;
import org.apache.cxf.message.Message;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.jackson.CustomObjectMapper;
import org.robbins.flashcards.repository.util.FieldInitializerUtil;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Component("partialResponseFilter")
public class PartialResponseFilter implements ResponseHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartialResponseFilter.class);

    @Inject
    private FieldInitializerUtil fieldInitializer;

    @Inject
    private CustomObjectMapper objectMapper;

    @Context
    private UriInfo uriInfo;

    @Override
    public Response handleResponse(final Message m, final OperationResourceInfo ori,
            final Response response) {

        if (ori == null) {
            return null;
        }

        // exit now if not an http GET method
        if (!StringUtils.equals(ori.getHttpMethod(), "GET")) {
            return null;
        }

        // exit now if not a 200 response, no sense in apply filtering if not a
        // '200 OK'
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            return null;
        }

        // exit now if we are not returning json
        if (!ori.getProduceTypes().get(0).toString().equals(MediaType.APPLICATION_JSON)) {
            return null;
        }

        // get a reference to the response entity. the entity holds the payload
        // of our response
        Object entity = response.getEntity();

        // try to get the 'fields' parameter from the QueryString
        String fields = uriInfo.getQueryParameters().getFirst("fields");

        // if the 'fields' QueryString is blank, then check to see if we have
        // @DefaultValue for 'fields'
        if (StringUtils.isBlank(fields)) {
            // get the Parameters for this Resource
            List<Parameter> parameters = ori.getParameters();
            for (Parameter parm : parameters) {
                // is the current Parameter named 'fields'?
                if (StringUtils.equals(parm.getName(), "fields")) {
                    // get the default value for 'fields'
                    fields = parm.getDefaultValue();

                    // now that we found 'fields', there's no need to keep
                    // looping
                    break;
                }
            }
            // If 'fields' is still blank then we don't have a value from either
            // the QueryString or @DefaultValue
            if (StringUtils.isBlank(fields)) {
                LOGGER.debug("Did not find 'fields' pararm for Resource '"
                        + uriInfo.getPath() + "'");
                return null;
            }
        }

        Set<String> filterProperties = this.getFieldsAsSet(fields);

        try {
            // is the entity a collection?
            if (entity instanceof Collection<?>) {
                fieldInitializer.initializeEntity((Collection<?>) entity,
                        filterProperties);
            } // is the entity an array?
            else if (entity instanceof Object[]) {
                fieldInitializer.initializeEntity((Object[]) entity, filterProperties);
            } else {
                fieldInitializer.initializeEntity(entity, filterProperties);
            }
        } catch (ServiceException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        // apply the Jackson filter and return the Response
        return applyFieldsFilter(filterProperties, entity, response);
    }

    // configure the Jackson filter for the speicified 'fields'
    private Response applyFieldsFilter(final Set<String> filterProperties,
            final Object object, final Response response) {
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("apiFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept(filterProperties));
        filterProvider.setFailOnUnknownId(false);

        return applyWriter(object, filterProvider, response);
    }

    // Get a JSON string from Jackson using the provided filter.
    // Note we are using the ObjectWriter rather than the ObjectMapper directly.
    // According to the Jackson docs,
    // "ObjectWriter instances are immutable and thread-safe: they are created by ObjectMapper"
    // You should not change config settings directly on the ObjectMapper while
    // using it (changing config of ObjectMapper is not thread-safe
    private Response applyWriter(final Object object,
            final SimpleFilterProvider filterProvider, final Response response) {

        try {
            ObjectWriter writer = objectMapper.writer(filterProvider);
            String jsonString = writer.writeValueAsString(object);

            // replace the Response entity with our filtered JSON string
            ResponseBuilder builder = Response.fromResponse(response);
            builder = builder.entity(jsonString);
            return builder.build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    // Convert the vectorized 'fields' parameter to a Set<String>
    private Set<String> getFieldsAsSet(final String fields) {
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
