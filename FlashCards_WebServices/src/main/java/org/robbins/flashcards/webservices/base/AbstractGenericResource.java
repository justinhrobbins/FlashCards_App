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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.robbins.flashcards.service.base.GenericJpaService;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.wordnik.swagger.annotations.ApiOperation;

public abstract class AbstractGenericResource <T, Serializable> extends AbstractResource implements GenericResource <T, Serializable> {
	
	private static Logger logger = Logger.getLogger(AbstractGenericResource.class);
	
	protected abstract GenericJpaService<T, Long> getService();

	@GET
	public List<T> list(@QueryParam("page") Integer page,
						@DefaultValue("25") @QueryParam("size") Integer size,
						@QueryParam("sort") String sort,
						@DefaultValue("asc") @QueryParam("direction") String direction) {
		
		List<T> entities = null;
		
	    try {
	    	
            // are we trying to use Pagination or Sorting? 
            // if not then go ahead and return findAll()
            if ((page == null) && (StringUtils.isEmpty(sort))) {
            	entities = getService().findAll();
            }
            // should we Page
            else if (page != null) {
                PageRequest pageRequest = getPageRequest(page, size, sort, direction);
                return getService().findAll(pageRequest);
            }
            // should we just Sort the list?
            else if (!StringUtils.isEmpty(sort)) {
	    		// get a sorted list
	    		Sort entitySort = getSort(sort, direction);
				entities = getService().findAll(entitySort);
			}
		} catch (InvalidDataAccessApiUsageException e) {
			logger.error(e.getMessage(), e);
			throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
					"Inavlid sort parameter: '" + sort + "'");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
					"Unable to 'list' entities");
		}

	    // did we get any results?
	    if (entities == null) {
			throw new GenericWebServiceException(Response.Status.NOT_FOUND,
					"Entities not found");			
		}
		
		return entities;
	}

	@GET
	@Path("/count")
	public Long count() {
		try {
			return getService().count();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GenericWebServiceException(
					Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GET
	@Path("/{id}")
	public T findOne(@PathParam("id") Long id) {

		T entity = getService().findOne(id);

		if (entity == null) {
			throw new GenericWebServiceException(Response.Status.NOT_FOUND,
					"Entity not found: " + id);
		}
		return entity;
	}
	
	@POST
	public T post(T entity) {
		try {
			return getService().save(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR,
					"Unable to 'post' entity");
		}
	}
	
	@PUT
	@Path("/{id}")
	public Response put(@PathParam("id") Long id, T entity) {
		try {
			getService().save(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR,
					"Unable to 'put' entity: " + id);
		}
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Delete", responseClass = "void")
	public Response delete(@PathParam("id") Long id) {
		try {
			getService().delete(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR,
					"Unable to 'delete' entity: " + id);
		}
		return Response.noContent().build();
	}

	@POST
	@Path("/{id}/update")
	public Response update(@PathParam("id") Long id, T updatedEntity) {
		try {
			// get the original entity from the db
			T originalEntity = getService().findOne(id);

			// merge the original with the updated entity
			this.merge(updatedEntity, originalEntity);

			// persist the entity back to the db
			getService().save(originalEntity);

			// return the response
			return Response.noContent().build();

		} catch (Exception e) {
			throw new GenericWebServiceException(
					Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	// update the target entity with any non-null field in the source entity
	private void merge(T source, T target) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(source.getClass());
		} catch (IntrospectionException e) {
			logger.error(e.getMessage(), e);
			throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
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
					throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
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
						throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
					}
				}
			}
		}
	}
}
