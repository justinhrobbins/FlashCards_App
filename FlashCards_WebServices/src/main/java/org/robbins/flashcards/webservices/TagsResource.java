
package org.robbins.flashcards.webservices;

import com.sun.jersey.api.JResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.webservices.base.AbstractGenericResource;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/tags/")
@Component("tagsResource")
@Api(value = "/v1/tags", description = "Operations about Tags")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class TagsResource extends AbstractGenericResource<TagDto, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagsResource.class);

    @Inject
	@Qualifier("presentationTagFacade")
    private TagFacade tagFacade;

    @Override
    protected GenericCrudFacade<TagDto, String> getFacade() {
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
    public Response put(@PathParam("id") final String id, final TagDto dto) {

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

    @Override
    public JResponse<List<TagDto>> list(final Integer page,  final Integer size,
                                        final String sort, final String direction,
                                        final String fields) {

        List<TagDto> entities;

        try {
            entities = getFacade().list(page, size, sort, direction,
                    this.getFieldsAsSet(fields));
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
                    "Inavlid sort parameter: '" + sort + "'", e);
        } catch (FlashcardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        if (CollectionUtils.isEmpty(entities)) {
            entities = new ArrayList<>();
        }

        return JResponse.ok(entities).build();
    }

    @GET
    public JResponse<List<TagDto>> list(@PathParam("flashcardId") final String flashcardId,
                                        @PathParam("userId") final String userId,
                                        @QueryParam("page") final Integer page,
                                        @DefaultValue("25") @QueryParam("size") final Integer size,
                                        @QueryParam("sort") final String sort,
                                        @DefaultValue("asc") @QueryParam("direction") final String direction,
                                        @QueryParam("fields") final String fields) {

        LOGGER.debug("flashcardId: {}", flashcardId);
        LOGGER.debug("userId: {}", userId);

        if (flashcardId != null) {
            return listTagsForFlashcard(flashcardId, page, size, sort, direction, fields);
        }
        else if (userId != null) {
            return listTagsForUser(userId, page, size, sort, direction, fields);
        }
        else {
            return list(page, size, sort, direction, fields);
        }
    }

    private JResponse<List<TagDto>> listTagsForFlashcard(final String flashcardId, final Integer page,
                                                         final Integer size, final String sort,
                                                         final String direction, final String fields) {
        try {
            List<TagDto> entities = tagFacade.findTagsForFlashcard(flashcardId, this.getFieldsAsSet(fields));
            if (CollectionUtils.isEmpty(entities)) {
                throw new GenericWebServiceException(Response.Status.NOT_FOUND,
                        "Tags not found for Flashcard: " + flashcardId);
            }
            return JResponse.ok(entities).build();
        } catch (FlashcardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    private JResponse<List<TagDto>> listTagsForUser(final String userId, final Integer page,
                                                         final Integer size, final String sort,
                                                         final String direction, final String fields) {
        try {
            List<TagDto> entities = tagFacade.findByCreatedBy(userId, this.getFieldsAsSet(fields));
            if (CollectionUtils.isEmpty(entities)) {
                throw new GenericWebServiceException(Response.Status.NOT_FOUND,
                        "Tags not found for User: " + userId);
            }
            return JResponse.ok(entities).build();
        } catch (FlashcardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Path("/{tagId}/flashcards")
    public Class findFlashcardsForTag() {
        return FlashCardsResource.class;
    }
}
