
package org.robbins.flashcards.webservices;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.ServiceException;

import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.robbins.flashcards.webservices.base.AbstractGenericResource;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/v1/flashcards/")
@Component("flashCardsResource")
@Api(value = "/v1/flashcards", description = "Operations about FlashCards")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class FlashCardsResource extends AbstractGenericResource<FlashCardDto, Long> {

    @Inject
	@Qualifier("presentationFlashcardFacade")
    private FlashcardFacade flashcardFacade;

    @Override
    protected GenericCrudFacade<FlashCardDto> getFacade() {
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

        return Long.valueOf(searchFlashCards(null, null, question, tags).length);
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
                    tagsSet.add(new TagDto(Long.parseLong(st.nextToken())));
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
        if (dto.getCreatedBy() == null) {
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
}
