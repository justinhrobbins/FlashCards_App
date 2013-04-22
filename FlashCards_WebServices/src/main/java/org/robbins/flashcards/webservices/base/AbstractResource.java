package org.robbins.flashcards.webservices.base;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.core.Response;

import org.apache.cxf.common.util.StringUtils;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public abstract class AbstractResource {
	
    protected PageRequest getPageRequest(Integer page, Integer size, String sortOrder, String sortDirection) {
        // are we Sorting too?
        if (!StringUtils.isEmpty(sortOrder)) {
                Sort sort = getSort(sortOrder, sortDirection);
                return new PageRequest(page, size, sort);
        } else {
                return new PageRequest(page, size);
        }               
    }
	
	protected Sort getSort(String sort, String order) {
		if ((StringUtils.isEmpty(order)) || (order.equals("asc"))) {
			return new Sort(Direction.ASC, order);
		} else if (order.equals("desc")) {
			return new Sort(Direction.DESC, order);
		}
		else {
			throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
					"Sort order must be 'asc' or 'desc'.  '" + order + "' is not an acceptable sort order");
		}
	}

	@PostConstruct
	public void postConstruct() {
	}
 
	@PreDestroy
	public void preDestroy() {
	}
}
