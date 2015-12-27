package org.robbins.flashcards.webservices.base;

import com.sun.jersey.api.JResponse;
import com.wordnik.swagger.annotations.ApiOperation;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGenericListingResource<T, ID extends Serializable> extends AbstractGenericResource<T, ID> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGenericListingResource.class);

    @GET
    @ApiOperation(value = "List")
    @Override
    public JResponse<List<T>> list(@QueryParam("page") final Integer page,
                                   @DefaultValue("25") @QueryParam("size") final Integer size,
                                   @QueryParam("sort") final String sort,
                                   @DefaultValue("asc") @QueryParam("direction") final String direction,
                                   @QueryParam("fields") final String fields) {

        List<T> entities;

        try {
            entities = getFacade().list(page, size, sort, direction,
                    this.getFieldsAsSet(fields));
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
                    "Inavlid sort parameter: '" + sort + "'", e);
        } catch (FlashcardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        // did we get any results?
        if ((entities == null) || (entities.size() == 0)) {
            entities = new ArrayList<>();
        }

        return JResponse.ok(entities).build();
    }
}
