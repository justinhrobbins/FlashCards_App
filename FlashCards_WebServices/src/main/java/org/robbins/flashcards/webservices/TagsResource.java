
package org.robbins.flashcards.webservices;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.webservices.base.AbstractGenericResource;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/v1/tags/")
@Component("tagsResource")
@Api(value = "/v1/tags", description = "Operations about Tags")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class TagsResource extends AbstractGenericResource<TagDto, Long> {

    @Inject
	@Qualifier("presentationTagFacade")
    private TagFacade tagFacade;

    @Override
    protected GenericCrudFacade<TagDto> getFacade() {
        return tagFacade;
    }

    @GET
    @Path("/search")
    @ApiOperation(value = "Find Tag by Name", response = TagDto.class)
    public TagDto searchByName(@QueryParam("name") final String name) {

        TagDto tagDto;
        try {
            tagDto = tagFacade.findByName(name);
        }
        catch(FlashcardsException e)
        {
            throw new GenericWebServiceException(
                    Response.Status.INTERNAL_SERVER_ERROR, e);
        }
        if (tagDto == null) {
            throw new GenericWebServiceException(Response.Status.NOT_FOUND,
                    "Entity not found: " + name);
        }
        return tagDto;
    }

    @Override
    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Replace a Tag")
    public Response put(@PathParam("id") final Long id, final TagDto dto) {

        if (dto.getCreatedBy() == null) {
            TagDto orig;
            try {
                orig = tagFacade.findOne(id);
            } catch (FlashcardsException e) {
                throw new GenericWebServiceException(
                        Response.Status.INTERNAL_SERVER_ERROR, e);
            }
            dto.setFlashcards(orig.getFlashcards());
            dto.setCreatedBy(orig.getCreatedBy());
            dto.setCreatedDate(orig.getCreatedDate());
        }
        return super.put(id, dto);
    }
}
