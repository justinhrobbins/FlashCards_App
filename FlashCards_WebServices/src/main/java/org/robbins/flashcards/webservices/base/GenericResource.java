
package org.robbins.flashcards.webservices.base;

import org.robbins.flashcards.dto.BatchLoadingReceiptDto;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

public interface GenericResource<T, ID extends Serializable> {

    Response list(Integer page, Integer size, String sort,
            String direction, String fields);

    Long count();

    T findOne(final ID id, final String fields);

    T post(final T entity);

    BatchLoadingReceiptDto post(final List<T> entity);

    Response put(final ID id, final T entity);

    Response delete(final ID id);

    Response update(final ID id, final T updatedEntity);
}
