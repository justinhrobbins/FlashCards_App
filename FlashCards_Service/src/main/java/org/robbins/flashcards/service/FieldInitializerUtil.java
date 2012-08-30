package org.robbins.flashcards.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

@Service
public class FieldInitializerUtil {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	
	public void initializeField(Object entity, String field) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		 EntityManager em = entityManagerFactory.createEntityManager();
		 
		 PersistenceUnitUtil unitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
		 
		 if (PropertyUtils.isReadable(entity, field)) {
			 if (!unitUtil.isLoaded(entity, field)) {
				 Object value = PropertyUtils.getProperty(entity, field);
				 if (value instanceof Collection<?>) {
					 initializeCollection((Collection<?>) value);	 
				 }
			 }			 
		 }
	}
	
	private void initializeCollection(Collection<?> collection) {
	    if(collection == null) {
	        return;
	    }
	    collection.iterator().hasNext();
	}
}
