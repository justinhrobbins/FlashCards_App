
package org.robbins.flashcards.webservices.base;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.sun.jersey.api.JResponse;
import com.wordnik.swagger.annotations.ApiOperation;

public abstract class AbstractGenericResource<T, Serializable> extends AbstractResource
        implements GenericResource<T, Serializable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGenericResource.class);

    protected abstract GenericCrudFacade<T> getFacade();


    @GET
    @ApiOperation(value = "List")
    @Override
    public JResponse<List<T>> list(@QueryParam("page") final Integer page,
            @DefaultValue("25") @QueryParam("size") final Integer size,
            @QueryParam("sort") final String sort,
            @DefaultValue("asc") @QueryParam("direction") final String direction,
            @QueryParam("fields") final String fields) {

        List<T> entities = null;

        try {
            entities = getFacade().list(page, size, sort, direction,
                    this.getFieldsAsSet(fields));
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
                    "Inavlid sort parameter: '" + sort + "'", e);
        } catch (ServiceException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        // did we get any results?
        if ((entities == null) || (entities.size() == 0)) {
            throw new GenericWebServiceException(Response.Status.NOT_FOUND,
                    "Entities not found");
        }

        return JResponse.ok(entities).build();
    }

    @Override
    @GET
    @Path("/count")
    @ApiOperation(value = "Count")
    public Long count() {
        return getFacade().count();
    }

    @Override
    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get one")
    public T findOne(@PathParam("id") final Long id,
            @QueryParam("fields") final String fields) {

        T entity;
        try {
            entity = getFacade().findOne(id, this.getFieldsAsSet(fields));
        } catch (ServiceException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        if (entity == null) {
            throw new GenericWebServiceException(Response.Status.NOT_FOUND,
                    "Entity not found: " + id);
        }
        return entity;
    }

    @Override
    @POST
    @ApiOperation(value = "Create")
    public T post(final T entity) {
        try {
            return getFacade().save(entity);
        } catch (ServiceException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Replace")
    public Response put(@PathParam("id") final Long id, final T entity) {
        try {
            getFacade().save(entity);
        } catch (ServiceException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        return Response.noContent().build();
    }

    @Override
    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete")
    public Response delete(@PathParam("id") final Long id) {
        getFacade().delete(id);

        return Response.noContent().build();
    }

    @Override
    @POST
    @Path("/{id}/update")
    @ApiOperation(value = "Update")
    public Response update(@PathParam("id") final Long id, final T updatedEntity) {
        // get the original entity from the db
        T originalEntity;
        try {
            originalEntity = getFacade().findOne(id);
        } catch (ServiceException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        // merge the original with the updated entity
        this.merge(updatedEntity, originalEntity);

        // persist the entity back to the db
        try {
            getFacade().save(originalEntity);
        } catch (ServiceException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        // return the response
        return Response.noContent().build();
    }

    // update the target entity with any non-null field in the source entity
    private void merge(final T source, final T target) {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(source.getClass());
        } catch (IntrospectionException e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR,
                    e.getMessage(), e);
        }

        // Iterate over all the attributes
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

            // Only copy writable attributes
            if (descriptor.getWriteMethod() != null) {
                Object newValue;
                try {
                    newValue = descriptor.getReadMethod().invoke(source);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new GenericWebServiceException(
                            Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), e);
                }

                // Only copy values values where the destination values is not null
                // Don't bother updating the 'identity' and 'version' fields
                // which are part of the AbstractPersistentObject
                if ((newValue != null) && (!descriptor.getName().equals("identity"))
                        && (!descriptor.getName().equals("version"))) {
                    LOGGER.debug("Updating field '" + descriptor.getName() + "' to: '"
                            + newValue + "'");
                    try {
                        descriptor.getWriteMethod().invoke(target, newValue);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                        throw new GenericWebServiceException(
                                Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), e);
                    }
                }
            }
        }
    }
}
