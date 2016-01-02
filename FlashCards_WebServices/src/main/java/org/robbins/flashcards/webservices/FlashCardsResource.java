
package org.robbins.flashcards.webservices;

import com.sun.jersey.api.JResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.webservices.base.AbstractGenericResource;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/flashcards/")
@Component("flashCardsResource")
@Api(value = "/v1/flashcards", description = "Operations about FlashCards")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class FlashCardsResource extends AbstractGenericResource<FlashCardDto, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlashCardsResource.class);

    @Inject
	@Qualifier("presentationFlashcardFacade")
    private FlashcardFacade flashcardFacade;

    @Override
    protected GenericCrudFacade<FlashCardDto, Long> getFacade() {
        return flashcardFacade;
    }

    @GET
    @Path("/search")
    @ApiOperation(value = "Search for FlashCards", response = FlashCardDto.class)
    public FlashCardDto[] search(@QueryParam("page") final Integer page,
            @DefaultValue("25") @QueryParam("size") final Integer size,
            @QueryParam("question") final String question,
            @QueryParam("tags") final String tags) {

        return searchFlashCards(page, size, question, tags);
    }

    @GET
    @Path("/search/count")
    @ApiOperation(value = "Get a count of FlashCards", response = Long.class)
    public Long searchCount(@QueryParam("question") final String question,
            @QueryParam("tags") final String tags) {

        return (long) searchFlashCards(null, null, question, tags).length;
    }

    private FlashCardDto[] searchFlashCards(final Integer page, final Integer size,
            final String question, final String tags) {

        try {
            // find by Question
            if (!StringUtils.isBlank(question)) {
                // are we using pagination?
                if (page != null) {
                    List<FlashCardDto> results;
                    results = flashcardFacade.findByQuestionLike(question,
                            new PageRequest(page, size));
                    return results.toArray(new FlashCardDto[results.size()]);
                } else {
                    List<FlashCardDto> results = flashcardFacade.findByQuestionLike(question);
                    return results.toArray(new FlashCardDto[results.size()]);
                }
            }
            // find by Tags
            if (!StringUtils.isBlank(tags)) {
                StringTokenizer st = new StringTokenizer(tags, ",");
                Set<TagDto> tagsSet = new HashSet<TagDto>();
                while (st.hasMoreTokens()) {
                    tagsSet.add(new TagDto(Long.valueOf(st.nextToken())));
                }
                // are we using Pagination?
                if (page != null) {
                    List<FlashCardDto> results = flashcardFacade.findByTagsIn(tagsSet,
                            new PageRequest(page, size));
                    return results.toArray(new FlashCardDto[results.size()]);
                } else {
                    List<FlashCardDto> results = flashcardFacade.findByTagsIn(tagsSet);
                    return results.toArray(new FlashCardDto[results.size()]);
                }
            } else {
                throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
                        "Invalid 'search' parameters");
            }
        } catch (FlashcardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    @PUT
    @Path("/{id}")
    public Response put(@PathParam("id") final Long id, final FlashCardDto dto) {

        // some client apps don't know the Created By and Created Date, so make
        // sure we set it
        if (dto.getCreatedBy().equals(0L)) {
            FlashCardDto orig;
            try {
                orig = flashcardFacade.findOne(id, null);
            } catch (FlashcardsException e) {
                throw new GenericWebServiceException(
                        Response.Status.INTERNAL_SERVER_ERROR, e);
            }
            dto.setCreatedBy(orig.getCreatedBy());
            dto.setCreatedDate(orig.getCreatedDate());
        }

        return super.put(id, dto);
    }

    @Override
    public JResponse<List<FlashCardDto>> list(final Integer page,  final Integer size,
                                        final String sort, final String direction,
                                        final String fields) {

        List<FlashCardDto> entities;

        try {
            entities = getFacade().list(page, size, sort, direction,
                    this.getFieldsAsSet(fields));
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
                    "Invalid sort parameter: '" + sort + "'", e);
        } catch (FlashcardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        if (CollectionUtils.isEmpty(entities)) {
            entities = new ArrayList<>();
        }

        return JResponse.ok(entities).build();
    }

    @GET
    public JResponse<List<FlashCardDto>> list(@PathParam("tagId") final Long tagId,
                                              @PathParam("userId") final Long userId,
                                              @QueryParam("page") final Integer page,
                                              @DefaultValue("25") @QueryParam("size") final Integer size,
                                              @QueryParam("sort") final String sort,
                                              @DefaultValue("asc") @QueryParam("direction") final String direction,
                                              @QueryParam("fields") final String fields) {

        if (tagId != null) {
            return listFlashcardsForTag(tagId, page, size, sort, direction, fields);
        }
        else if (userId != null) {
            return listFlashcardsForUser(userId, page, size, sort, direction, fields);
        }
        else {
            return list(page, size, sort, direction, fields);
        }
    }

    private JResponse<List<FlashCardDto>> listFlashcardsForTag(final Long tagId, final Integer page,
                                                               final Integer size, final String sort,
                                                               final String direction, final String fields) {
        try {
            List<FlashCardDto> entities = flashcardFacade.findFlashcardsForTag(tagId, this.getFieldsAsSet(fields));
            if (CollectionUtils.isEmpty(entities)) {
                throw new GenericWebServiceException(Response.Status.NOT_FOUND,
                        "Flashcards not found for Tag: " + tagId);
            }
            return JResponse.ok(entities).build();
        } catch (FlashcardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    private JResponse<List<FlashCardDto>> listFlashcardsForUser(final Long userId, final Integer page,
                                                               final Integer size, final String sort,
                                                               final String direction, final String fields) {
        try {
            List<FlashCardDto> entities = flashcardFacade.findByCreatedBy(userId, this.getFieldsAsSet(fields));
            if (CollectionUtils.isEmpty(entities)) {
                throw new GenericWebServiceException(Response.Status.NOT_FOUND,
                        "Flashcards not found for User: " + userId);
            }
            return JResponse.ok(entities).build();
        } catch (FlashcardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Path("/{flashcardId}/tags")
    public Class findTagsForFlashcard() {
        return TagsResource.class;
    }
}
