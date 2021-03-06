
package org.robbins.flashcards.webservices.base;

import io.swagger.annotations.ApiOperation;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.DataIntegrityException;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractGenericResource<T, ID extends Serializable> extends AbstractResource
        implements GenericResource<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGenericResource.class);

    protected abstract GenericPagingAndSortingService<T, ID> getService();

    @Override
    @GET
    @Path("/count")
    @ApiOperation(value = "Count")
    public Long count() {
        return getService().count();
    }

    @Override
    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get one")
    public T findOne(@PathParam("id") final ID id,
            @QueryParam("fields") final String fields) {

        final T entity;
        try {
            entity = getService().findOne(id, this.getFieldsAsSet(fields));
        } catch (final FlashCardsException e) {
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
            LOGGER.debug("Received post request for {}", entity.getClass().getSimpleName());
            return getService().save(entity);
        } catch (final DataIntegrityException e) {
            throw new GenericWebServiceException(Response.Status.BAD_REQUEST, e);
        }
        catch (final FlashCardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    @Path("/batch")
    @POST
    @ApiOperation(value = "Create in batch")
    public BatchLoadingReceiptDto post(final List<T> entities) {
        LOGGER.debug("Received batch load request for {} {}", entities.size(), entities.get(0).getClass().getSimpleName());
        try {
            return getService().save(entities);
        } catch (final DataIntegrityException e) {
            throw new GenericWebServiceException(Response.Status.BAD_REQUEST, e);
        }
        catch (final FlashCardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Replace")
    public Response put(@PathParam("id") final ID id, final T entity) {
        try {
            getService().save(entity);
        } catch (final FlashCardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
        return Response.noContent().build();
    }

    @Override
    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete")
    public Response delete(@PathParam("id") final ID id) {
        getService().delete(id);

        return Response.noContent().build();
    }

    @Override
    @POST
    @Path("/{id}/update")
    @ApiOperation(value = "Update")
    public Response update(@PathParam("id") final ID id, final T updatedEntity) {
        // get the original entity from the db
        final T originalEntity;
        try {
            originalEntity = getService().findOne(id);
        } catch (final FlashCardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        // merge the original with the updated entity
        this.merge(updatedEntity, originalEntity);

        // persist the entity back to the db
        try {
            getService().save(originalEntity);
        } catch (final FlashCardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        // return the response
        return Response.noContent().build();
    }

    // update the target entity with any non-null field in the source entity
    private void merge(final T source, final T target) {
        final BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(source.getClass());
        } catch (final IntrospectionException e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR,
                    e.getMessage(), e);
        }

        Arrays.stream(beanInfo.getPropertyDescriptors())
                .forEach(descriptor -> mergeField(source, target, descriptor));
    }

    private void mergeField(final T source, final T target, final PropertyDescriptor descriptor) {
        // Only copy writable attributes
        if (descriptor.getWriteMethod() != null) {
            final Object newValue;
            try {
                newValue = descriptor.getReadMethod().invoke(source);
            } catch (final Exception e) {
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
                } catch (final Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new GenericWebServiceException(
                            Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), e);
                }
            }
        }
    }
}
