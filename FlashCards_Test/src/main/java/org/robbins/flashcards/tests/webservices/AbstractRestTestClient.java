
package org.robbins.flashcards.tests.webservices;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractRestTestClient<E> extends BaseRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRestTestClient.class);

    /**
     * Gets an array entities.
     *
     * @param url the url
     * @param clazz the clazz
     * @return the array of entities
     */
    public E[] getEntityList(final String url, final Class<E[]> clazz) {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        // make the REST call
        // we use 'exchange' here instead of 'getForObject' because we have to
        // include the Authentication header
        ResponseEntity<E[]> response = getRestTemplate().exchange(url, HttpMethod.GET,
                httpEntity, clazz);

        // retrieve the entities from the Response
        E[] entityList = response.getBody();
        return entityList;
    }

    /**
     * Gets a count of entities.
     *
     * @param url the url
     * @return the count of entities
     */
    public Long getEntityCount(String url) {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        url += "count/";

        // make the REST call
        // we use 'exchange' here instead of 'getForObject' because we have to
        // include the Authentication header
        ResponseEntity<Long> response = getRestTemplate().exchange(url, HttpMethod.GET,
                httpEntity, Long.class);

        // retrieve the entity count from the Response
        Long entityCount = response.getBody();
        return entityCount;
    }

    /**
     * Gets the entity.
     *
     * @param url the url
     * @param id the id
     * @param clazz the clazz
     * @return the entity
     */
    public E getEntity(final String url, final Long id, final Class<E> clazz) {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        // set the URL parameter
        Map<String, String> vars = Collections.singletonMap("id", String.valueOf(id));

        // make the REST call
        // we use 'exchange' here instead of 'getForObject' because we have to
        // include the Authentication header
        ResponseEntity<E> response = getRestTemplate().exchange(url, HttpMethod.GET,
                httpEntity, clazz, vars);

        // retrieve the entity from the Response
        E entity = response.getBody();
        return entity;
    }

    /**
     * Search for a single entity.
     *
     * @param url the url
     * @param uriVariables the uri variables
     * @param clazz the clazz
     * @return the e[]
     */
    public E searchSingleEntity(final String url, final Map<String, String> uriVariables,
            final Class<E> clazz) {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        // make the REST call
        // we use 'exchange' here instead of 'getForObject' because we have to
        // include the Authentication header
        ResponseEntity<E> response = getRestTemplate().exchange(url, HttpMethod.GET,
                httpEntity, clazz, uriVariables);

        // retrieve the entities from the Response
        return response.getBody();
    }

    /**
     * Search entities.
     *
     * @param url the url
     * @param uriVariables the uri variables
     * @param clazz the clazz
     * @return the e[]
     */
    public E[] searchEntities(final String url, final Map<String, String> uriVariables,
            final Class<E[]> clazz) {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        // make the REST call
        // we use 'exchange' here instead of 'getForObject' because we have to
        // include the Authentication header
        ResponseEntity<E[]> response = getRestTemplate().exchange(url, HttpMethod.GET,
                httpEntity, clazz, uriVariables);

        // retrieve the entities from the Response
        return response.getBody();
    }

    /**
     * Search count.
     *
     * @param url the url
     * @param uriVariables the uri variables
     * @return the count of entities returned by the search
     */
    public Long searchCount(final String url, final Map<String, String> uriVariables) {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        // make the REST call
        // we use 'exchange' here instead of 'getForObject' because we have to
        // include the Authentication header
        ResponseEntity<Long> response = getRestTemplate().exchange(url, HttpMethod.GET,
                httpEntity, Long.class, uriVariables);

        // retrieve the entity count from the Response
        Long entityCount = response.getBody();
        return entityCount;
    }

    /**
     * Post entity.
     *
     * @param url the url
     * @param entity the entity
     * @param clazz the clazz
     * @return the e
     */
    public E postEntity(final String url, final E entity, final Class<E> clazz) {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(entity, getAuthHeaders());

        // make the REST call
        return getRestTemplate().postForObject(url, httpEntity, clazz);
    }

    /**
     * Post entity.
     *
     * @param url the url
     * @param entity the entity
     * @param uriVariables the uri variables
     * @param clazz the clazz
     * @return the e
     */
    public E postEntity(final String url, final E entity,
            final Map<String, String> uriVariables, final Class<E> clazz) {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(entity, getAuthHeaders());

        // make the REST call
        return getRestTemplate().postForObject(url, httpEntity, clazz, uriVariables);
    }

    /**
     * Put entity.
     *
     * @param url the url
     * @param id the id
     * @param entity the entity
     * @return the http status
     */
    public HttpStatus putEntity(final String url, final Long id, final E entity) {
        HttpHeaders httpHeaders = getAuthHeaders();
        httpHeaders.set("Accept", "application/json");

        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(entity, httpHeaders);

        // set the URL parameter
        Map<String, String> vars = Collections.singletonMap("id", String.valueOf(id));

        // make the REST call
        @SuppressWarnings("rawtypes")
        ResponseEntity response = getRestTemplate().exchange(url, HttpMethod.PUT,
                httpEntity, ResponseEntity.class, vars);
        return response.getStatusCode();
    }

    /**
     * Update the entity.
     *
     * @param url the url
     * @param uriVariables the uri variables
     * @param entity the entity
     * @return the http status
     */
    public HttpStatus updateEntity(final String url,
            final Map<String, String> uriVariables, final E entity) {

        HttpHeaders httpHeaders = getAuthHeaders();
        httpHeaders.set("Accept", "application/json");

        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(entity, httpHeaders);

        // make the REST call
        @SuppressWarnings("rawtypes")
        ResponseEntity response = getRestTemplate().exchange(url, HttpMethod.POST,
                httpEntity, ResponseEntity.class, uriVariables);
        return response.getStatusCode();
    }

    /**
     * Put entity.
     *
     * @param url the url
     * @param uriVariables the uri variables
     * @param entity the entity
     * @return the http status
     */
    public HttpStatus putEntity(final String url, final Map<String, String> uriVariables,
            final E entity) {
        HttpHeaders httpHeaders = getAuthHeaders();
        httpHeaders.set("Accept", "application/json");

        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(entity, httpHeaders);

        // make the REST call
        @SuppressWarnings("rawtypes")
        ResponseEntity response = getRestTemplate().exchange(url, HttpMethod.PUT,
                httpEntity, ResponseEntity.class, uriVariables);
        return response.getStatusCode();
    }

    /**
     * Delete entity.
     *
     * @param url the url
     * @param id the id
     * @return the http status
     */
    public HttpStatus deleteEntity(final String url, final Long id) {
        HttpHeaders httpHeaders = getAuthHeaders();
        httpHeaders.set("Accept", "application/json");

        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        // set the URL parameter
        Map<String, String> vars = Collections.singletonMap("id", String.valueOf(id));

        // make the REST call
        @SuppressWarnings("rawtypes")
        ResponseEntity response = getRestTemplate().exchange(url, HttpMethod.DELETE,
                httpEntity, ResponseEntity.class, vars);
        return response.getStatusCode();
    }
}
