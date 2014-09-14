
package org.robbins.flashcards.tests.webservices;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robbins.flashcards.client.GenericRestCrudFacade;
import org.robbins.flashcards.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Persistable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class GenericEntityRestTest<E extends Persistable<Long>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericEntityRestTest.class);

    /**
     * Sets the entity.
     *
     * @param entity the new entity
     */
    public abstract void setEntity(E entity);

    /**
     * Gets the entity.
     *
     * @return the entity
     */
    public abstract E getEntity();

    public abstract GenericRestCrudFacade<E> getClient();

    /**
     * Test get entity list.
     */
    @Test
    public void testGetEntityList() throws ServiceException {
        final List<E> entityList = getClient().list();

        assertTrue(entityList.size() > 0);
    }

    /**
     * Test get entity count.
     */
    @Test
    public void testGetEntityCount() {
        final Long entityCount = getClient().count();

        assertTrue(entityCount > 0);
    }

    /**
     * Test get entity.
     */
    @Test
    public void testGetEntity() throws ServiceException {
        final E retrievedEntity = getClient().findOne(getEntity().getId());

        assertEquals(retrievedEntity.getId(), getEntity().getId());
    }

    /**
     * Test post entity.
     */
    @Test
    public void testPostEntity() {
        // if the Entity exists then we know it has been created successfully in @Before
        assertTrue(getEntity() != null);
    }

    /**
     * Test put entity.
     */
    @Test
    public void testPutEntity() throws ServiceException {

        getClient().put(getEntity());

        // double-check the Entity info was updated by re-pulling the Entity
        final E retrievedEntity = getClient().findOne(getEntity().getId());
        assertEquals(retrievedEntity.getId(), getEntity().getId());
    }

    /**
     * Test delete entity.
     */
    @Test
    public void testDeleteEntity() {
        getClient().delete(getEntity().getId());

        // set the Entity to null so we don't try to delete it again in @After
        setEntity(null);
    }

    /**
     * Post entity.
     */
    public void postEntity() throws ServiceException {
        setEntity(getClient().save(getEntity()));
    }

    /**
     * Before.
     */
    @Before
    public void before() throws ServiceException{
        LOGGER.info("******************** BEFORE TEST ********************");

        postEntity();
    }

    /**
     * After.
     */
    @After
    public void after() {
        LOGGER.info("******************** AFTER TEST ********************");

        if (getEntity() != null) {
            getClient().delete(getEntity().getId());
        }
    }
}
