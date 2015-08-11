
package org.robbins.flashcards.webservices.base;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.core.Response;

import com.sun.jersey.api.JResponse;

public interface GenericResource<T, ID extends Serializable> {

    JResponse<List<T>> list(Integer page, Integer size, String sort,
            String direction, String fields);

    Long count();

    T findOne(final ID id, final String fields);

    T post(final T entity);

    Response post(final List<T> entity);

    Response put(final ID id, final T entity);

    Response delete(final ID id);

    Response update(final ID id, final T updatedEntity);
}
