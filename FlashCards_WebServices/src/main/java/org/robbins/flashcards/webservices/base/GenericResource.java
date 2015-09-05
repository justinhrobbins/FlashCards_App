
package org.robbins.flashcards.webservices.base;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.core.Response;

import com.sun.jersey.api.JResponse;
import org.robbins.flashcards.dto.BulkLoadingReceiptDto;

public interface GenericResource<T, ID extends Serializable> {

    JResponse<List<T>> list(Integer page, Integer size, String sort,
            String direction, String fields);

    Long count();

    T findOne(final ID id, final String fields);

    T post(final T entity);

    BulkLoadingReceiptDto post(final List<T> entity);

    Response put(final ID id, final T entity);

    Response delete(final ID id);

    Response update(final ID id, final T updatedEntity);
}
