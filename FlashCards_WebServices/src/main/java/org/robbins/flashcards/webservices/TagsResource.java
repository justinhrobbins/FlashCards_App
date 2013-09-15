package org.robbins.flashcards.webservices;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.base.GenericJpaService;
import org.robbins.flashcards.webservices.base.AbstractGenericResource;
import org.springframework.stereotype.Component;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/v1/tags/")
@Component("tagsResource")
@Api(value="/v1/tags", description = "Operations about Tags")
@Produces("application/json")
public class TagsResource extends AbstractGenericResource<Tag, Long> {

	@Inject
	private TagFacade tagFacade;
	
	protected GenericJpaService<Tag, Long> getFacade() {
		return tagFacade;
	}

	@GET
	@Path("/search")
	@ApiOperation(value = "Find Tag by Name", responseClass = "org.robbins.flashcards.model.Tag")
	public Tag searchByName(@QueryParam("name") String name) {
			return tagFacade.findByName(name);
	}
	
	@Override
	@PUT
	@ApiOperation(value = "Replace a Tag", responseClass = "void")
	public Response put(@PathParam("id") Long id, Tag entity) {

		if (entity.getCreatedBy() == null) {
			Tag orig = tagFacade.findOne(id);
			entity.setFlashcards(orig.getFlashcards());
			entity.setCreatedBy(orig.getCreatedBy());
			entity.setCreatedDate(orig.getCreatedDate());
		}
		return super.put(id,  entity);
	}
}