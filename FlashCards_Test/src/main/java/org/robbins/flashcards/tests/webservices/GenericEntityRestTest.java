
package org.robbins.flashcards.tests.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Persistable;
import org.springframework.http.HttpStatus;

public abstract class GenericEntityRestTest<E extends Persistable<Long>> extends
        AbstractRestTestClient<E> {

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

    /**
     * Gets the entity list url.
     *
     * @return the entity list url
     */
    public abstract String getEntityListUrl();

    /**
     * Gets the entity url.
     *
     * @return the entity url
     */
    public abstract String getEntityUrl();

    /**
     * Post entity url.
     *
     * @return the string
     */
    public abstract String postEntityUrl();

    /**
     * Put entity url.
     *
     * @return the string
     */
    public abstract String putEntityUrl();

    /**
     * Delete entity url.
     *
     * @return the string
     */
    public abstract String deleteEntityUrl();

    /**
     * Search url.
     *
     * @return the string
     */
    public abstract String searchUrl();

    // private Class<E> clazz;
    /**
     * Gets the clazz.
     *
     * @return the clazz
     */
    public abstract Class<E> getClazz();

    /**
     * Gets the clazz array.
     *
     * @return the clazz array
     */
    public abstract Class<E[]> getClazzArray();

    /**
     * Test get entity list.
     */
    @Test
    public void testGetEntityList() {
        // get a Entity
        E[] entityList = getEntityList(getEntityListUrl(), getClazzArray());

        // test that our get worked
        assertTrue(entityList.length > 0);
    }

    /**
     * Test get entity count.
     */
    @Test
    public void testGetEntityCount() {
        // get a count
        Long entityCount = getEntityCount(getEntityListUrl());

        // test that our get worked
        assertTrue(entityCount > 0);
    }

    /**
     * Test get entity.
     */
    @Test
    public void testGetEntity() {
        // get a Entity
        E retrievedEntity = getEntity(getEntityUrl(), getEntity().getId(), getClazz());

        // test that our get worked
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
    public void testPutEntity() {
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("id", String.valueOf(getEntity().getId()));

        // call the update for the RESTful API
        HttpStatus status = putEntity(putEntityUrl(), uriVariables, getEntity());
        assertEquals(status.toString(), "204");

        // double-check the Entity info was updated by re-pulling the Entity
        E retrievedEntity = getEntity(getEntityUrl(), getEntity().getId(), getClazz());
        assertEquals(retrievedEntity.getId(), getEntity().getId());
    }

    /**
     * Test delete entity.
     */
    @Test
    public void testDeleteEntity() {
        HttpStatus status = deleteEntity(deleteEntityUrl(), getEntity().getId());
        assertEquals(status.toString(), "204");

        // set the Entity to null so we don't try to delete it again in @After
        setEntity(null);
    }

    /**
     * Post entity.
     */
    public void postEntity() {
        setEntity(postEntity(postEntityUrl(), getEntity(), getClazz()));
    }

    /**
     * Before.
     */
    @Before
    public void before() {
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
            // go ahead and delete the Entity
            deleteEntity(getEntityUrl(), getEntity().getId());
        }
    }
}
