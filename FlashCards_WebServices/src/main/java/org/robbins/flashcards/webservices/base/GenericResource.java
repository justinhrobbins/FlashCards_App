
package org.robbins.flashcards.webservices.base;

import java.util.List;

import javax.ws.rs.core.Response;

import com.sun.jersey.api.JResponse;

public interface GenericResource<T, Serializable> {

    JResponse<List<T>> list(Integer page, Integer size, String sort,
            String direction, String fields);

    Long count();

    T findOne(Long id, String fields);

    T post(T entity);

    Response put(Long id, T entity);

    Response delete(Long id);

    Response update(Long id, T updatedEntity);
}
