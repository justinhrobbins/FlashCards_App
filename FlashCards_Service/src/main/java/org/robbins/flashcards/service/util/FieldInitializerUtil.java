package org.robbins.flashcards.service.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.robbins.flashcards.exceptions.ServiceException;
import org.springframework.stereotype.Component;

@Component
public class FieldInitializerUtil {

	private static Logger logger = Logger.getLogger(FieldInitializerUtil.class);
	
	@Inject
	private EntityManagerFactory entityManagerFactory;
	
	public void initializeField(Object entity, String field) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		 EntityManager em = entityManagerFactory.createEntityManager();
		 
		 PersistenceUnitUtil unitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
		 
		 if ( (PropertyUtils.isReadable(entity, field)) && (!unitUtil.isLoaded(entity, field)) ) {
				 Object value = PropertyUtils.getProperty(entity, field);
				 if (value instanceof Collection<?>) {
					 initializeCollection((Collection<?>) value);	 
				 }
		 }
	}

	public void initializeEntity(Collection<?> entities, Set<String> fields) throws ServiceException {
		for (Object entity : entities) {
			initializeEntity(entity, fields);
		}
	}

	public void initializeEntity(Object[] entities, Set<String> fields) throws ServiceException {
		for (Object entity : entities) {
			initializeEntity(entity, fields);
		}
	}

	// make sure each of the requested 'fields' are initialized by Hibernate
	public void initializeEntity(Object entity, Set<String> fields) throws ServiceException {

		if (entity == null) {
			return;
		}

		// loop through the values of the 'fields'
		for (String field : fields) {
			try {
				// Initialize the current 'field'
				// This is needed since Hibernate will not auto-initialize most
				// collections
				// Therefore, if we want to return the field in the response, we
				// need to make sure it is loaded
				initializeField(entity, field);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new ServiceException(
						e.getMessage(), e);
			}
		}
		return;
	}
	
	private void initializeCollection(Collection<?> collection) {
	    if(collection == null) {
	        return;
	    }
	    collection.iterator().hasNext();
	}
}
