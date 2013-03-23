package org.robbins.flashcards.webservices;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.TagService;
import org.robbins.flashcards.service.base.GenericJpaService;
import org.robbins.flashcards.webservices.base.AbstractGenericResource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Path("/tags/")
@Component("tagsResource")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Produces("application/json")
public class TagsResource extends AbstractGenericResource<Tag, Long> {
	
	private static Logger logger = Logger.getLogger(TagsResource.class);

	@Inject
	private TagService service;
	
	protected GenericJpaService<Tag, Long> getService() {
		return service;
	}

	@GET
	@Path("/search")
	@Produces("application/json")
	public Tag searchByName(@QueryParam("name") String name) {
			logger.debug("Entering search()");
			
			return service.findByName(name);
	}
	
	@Override
	public Response put(@PathParam("id") Long id, Tag entity) {

		if (entity.getCreatedBy() == null) {
			Tag orig = service.findOne(id);
			entity.setFlashcards(orig.getFlashcards());
			entity.setCreatedBy(orig.getCreatedBy());
			entity.setCreatedDate(orig.getCreatedDate());
		}
		return super.put(id,  entity);
	}
}
