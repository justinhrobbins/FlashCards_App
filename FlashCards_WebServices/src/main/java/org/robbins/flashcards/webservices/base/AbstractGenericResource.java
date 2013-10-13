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

import org.apache.log4j.Logger;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.sun.jersey.api.JResponse;
import com.wordnik.swagger.annotations.ApiOperation;

public abstract class AbstractGenericResource <T, Serializable> extends AbstractResource implements GenericResource <T, Serializable> {
	
	private static Logger logger = Logger.getLogger(AbstractGenericResource.class);
	
	protected abstract GenericCrudFacade<T> getFacade();

	@GET
	@Override
	public JResponse<List<T>> list(@QueryParam("page") Integer page,
						@DefaultValue("25") @QueryParam("size") Integer size,
						@QueryParam("sort") String sort,
						@DefaultValue("asc") @QueryParam("direction") String direction,
						@QueryParam("fields") String fields) {
		
		List<T> entities = null;
		
	    try {
            entities = getFacade().list(page, size, sort, direction, this.getFieldsAsSet(fields));
		} catch (InvalidDataAccessApiUsageException e) {
			logger.error(e.getMessage(), e);
			throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
					"Inavlid sort parameter: '" + sort + "'", e);
		} catch (ServiceException e) {
			throw new GenericWebServiceException(
					Response.Status.INTERNAL_SERVER_ERROR, e);
		}

	    // did we get any results?
	    if (entities == null) {
			throw new GenericWebServiceException(Response.Status.NOT_FOUND,
					"Entities not found");
		}
	    
	    return JResponse.ok(entities).build();
	}

	@GET
	@Path("/count")
	public Long count() {
		return getFacade().count();
	}
	
	@GET
	@Path("/{id}")
	public T findOne(@PathParam("id") Long id, @QueryParam("fields") String fields) {

		T entity;
		try {
			entity = getFacade().findOne(id, this.getFieldsAsSet(fields));
		} catch (ServiceException e) {
			throw new GenericWebServiceException(
					Response.Status.INTERNAL_SERVER_ERROR, e);
		}

		if (entity == null) {
			throw new GenericWebServiceException(Response.Status.NOT_FOUND,
					"Entity not found: " + id);
		}
		return entity;
	}
	
	@POST
	public T post(T entity) {
		return getFacade().save(entity);
	}
	
	@PUT
	@Path("/{id}")
	public Response put(@PathParam("id") Long id, T entity) {
		getFacade().save(entity);
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Delete")
	public Response delete(@PathParam("id") Long id) {
		getFacade().delete(id);

		return Response.noContent().build();
	}

	@POST
	@Path("/{id}/update")
	public Response update(@PathParam("id") Long id, T updatedEntity) {
		// get the original entity from the db
		T originalEntity;
		try {
			originalEntity = getFacade().findOne(id);
		} catch (ServiceException e) {
			throw new GenericWebServiceException(
					Response.Status.INTERNAL_SERVER_ERROR, e);
		}

		// merge the original with the updated entity
		this.merge(updatedEntity, originalEntity);

		// persist the entity back to the db
		getFacade().save(originalEntity);

		// return the response
		return Response.noContent().build();
	}
	
	// update the target entity with any non-null field in the source entity
	private void merge(T source, T target) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(source.getClass());
		} catch (IntrospectionException e) {
			logger.error(e.getMessage(), e);
			throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}

		// Iterate over all the attributes
		for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

			// Only copy writable attributes
			if (descriptor.getWriteMethod() != null) {
				Object newValue;
				try {
					newValue = descriptor.getReadMethod().invoke(source);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), e);
				}

				// Only copy values values where the destination values is not null
				// Don't bother updating the 'identity' and 'version' fields
				// which are part of the AbstractPersistentObject
				if ( (newValue != null) && (!descriptor.getName().equals("identity")) && (!descriptor.getName().equals("version")) ) {
					logger.debug("Updating field '" + descriptor.getName()
							+ "' to: '" + newValue + "'");
					try {
						descriptor.getWriteMethod().invoke(target, newValue);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), e);
					}
				}
			}
		}
	}
}
