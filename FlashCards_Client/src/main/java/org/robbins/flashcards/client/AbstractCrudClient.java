package org.robbins.flashcards.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.dto.BatchLoadingReceiptDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.exceptions.ServiceException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

public abstract class AbstractCrudClient<E extends AbstractPersistableDto, ID> extends AbstractClient implements GenericRestCrudFacade<E, ID> {

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
     * Post entity url.
     *
     * @return the string
     */
    public abstract String postBatchEntitiesUrl();

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

    /**
     * Update url.
     *
     * @return the string
     */
    public abstract String updateUrl();

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

    @Override
    public List<E> list() {
        return list(Optional.empty(), null);
    }

    @Override
    public List<E> list(final Optional<Pageable> page, final Set<String> fields) {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        final HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        final ResponseEntity<E[]> response = getRestTemplate().exchange(getEntityListUrl(), HttpMethod.GET,
                httpEntity, getClazzArray());

        final E[] entityList = response.getBody();
        return Arrays.asList(entityList);
    }

    @Override
    public List<E> list(final Optional<Pageable> page) {
        return list(page, null);
    }

    @Override
    public Long count() {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        final HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        final String url = getEntityListUrl() + "count/";

        final ResponseEntity<Long> response = getRestTemplate().exchange(url, HttpMethod.GET,
                httpEntity, Long.class);

        return response.getBody();
    }

    @Override
    public E findOne(final ID id) {
        return findOne(id, null);
    }

    @Override
    public E findOne(final ID id, Set<String> fields) {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        final HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        final Map<String, String> vars = Collections.singletonMap("id", String.valueOf(id));

        final ResponseEntity<E> response = getRestTemplate().exchange(getEntityUrl(), HttpMethod.GET,
                httpEntity, getClazz(), vars);

        return response.getBody();
    }

    @Override
    public E save(final E entity) throws ServiceException {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        final HttpEntity httpEntity = new HttpEntity(entity, getAuthHeaders());

        try {
            return getRestTemplate().postForObject(postEntityUrl(), httpEntity, getClazz());
        }
        catch (HttpClientErrorException e) {
            throw new ServiceException("Unble to save '" + entity.getClass().getSimpleName() + "' " + e.getMessage());
        }
        catch (Exception e) {
            throw new ServiceException("Unexpected exception" + e.getMessage());
        }
    }

    @Override
    public BatchLoadingReceiptDto save(List<E> entities) throws FlashCardsException
    {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        final HttpEntity httpEntity = new HttpEntity(entities, getAuthHeaders());

        try {
            return getRestTemplate().postForObject(postBatchEntitiesUrl(), httpEntity, BatchLoadingReceiptDto.class);
        }
        catch (HttpClientErrorException e) {
            throw new ServiceException("Unable to save '" + entities.getClass().getSimpleName() + "' " + e.getMessage());
        }
        catch (Exception e) {
            throw new ServiceException("Unexpected exception" + e.getMessage());
        }
    }

    @Override
    public void delete(final ID id) {
        final HttpHeaders httpHeaders = getAuthHeaders();
        httpHeaders.set("Accept", "application/json");

        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        final HttpEntity httpEntity = new HttpEntity(httpHeaders);

        final Map<String, String> vars = Collections.singletonMap("id", String.valueOf(id));

        getRestTemplate().exchange(deleteEntityUrl(), HttpMethod.DELETE,
                httpEntity, ResponseEntity.class, vars);
    }

    @Override
    public void update(final E entity) {
        final  HttpHeaders httpHeaders = getAuthHeaders();
        httpHeaders.set("Accept", "application/json");

        final Map<String, String> uriVariables = Collections.singletonMap("id", String.valueOf(entity.getId()));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        final  HttpEntity httpEntity = new HttpEntity(entity, httpHeaders);

        getRestTemplate().exchange(updateUrl(), HttpMethod.POST,
                httpEntity, ResponseEntity.class, uriVariables);
    }

    @Override
    public void put(final E entity) {
        final  HttpHeaders httpHeaders = getAuthHeaders();
        httpHeaders.set("Accept", "application/json");

        final Map<String, String> uriVariables = Collections.singletonMap("id", String.valueOf(entity.getId()));

        @SuppressWarnings({ "unchecked", "rawtypes" })
        final HttpEntity httpEntity = new HttpEntity(entity, httpHeaders);

        getRestTemplate().exchange(putEntityUrl(), HttpMethod.PUT,
                httpEntity, ResponseEntity.class, uriVariables);
    }

    @Override
    public List<E> findByCreatedBy(final ID userId, final Set<String> fields) throws FlashCardsException
    {
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("userId", String.valueOf(userId));
        return Arrays.asList(searchEntities(getEntityListUrl(), uriVariables, getClazzArray()));
    }

    public E searchSingleEntity(final Map<String, String> uriVariables) {
        // set the Authentication header
        @SuppressWarnings({ "unchecked", "rawtypes" })
        final HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        final ResponseEntity<E> response = getRestTemplate().exchange(searchUrl(), HttpMethod.GET,
                httpEntity, getClazz(), uriVariables);

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
        final HttpEntity httpEntity = new HttpEntity(getAuthHeaders());

        final ResponseEntity<E[]> response = getRestTemplate().exchange(url, HttpMethod.GET,
                httpEntity, clazz, uriVariables);

        return response.getBody();
    }
}
