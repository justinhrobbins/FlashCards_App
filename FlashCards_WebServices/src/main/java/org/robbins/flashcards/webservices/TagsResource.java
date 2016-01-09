
package org.robbins.flashcards.webservices;

import java.util.ArrayList;
import java.util.List;

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

import org.apache.commons.collections.CollectionUtils;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.util.PagingUtils;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.service.TagService;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;
import org.robbins.flashcards.webservices.base.AbstractGenericResource;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.JResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/tags/")
@Component("tagsResource")
@Api(value = "/v1/tags", description = "Operations about Tags")
@Produces({"application/xml", "application/json"})
@Consumes({"application/xml", "application/json"})
public class TagsResource extends AbstractGenericResource<TagDto, Long>
{

	private static final Logger LOGGER = LoggerFactory.getLogger(TagsResource.class);

	@Inject
	private TagService tagService;

	@Override
	protected GenericPagingAndSortingService<TagDto, Long> getService()
	{
		return tagService;
	}

	@GET
	@Path("/search")
	@ApiOperation(value = "Find Tag by Name", response = TagDto.class)
	public TagDto searchByName(@QueryParam("name") final String name)
	{
		TagDto tagDto;
		try
		{
			tagDto = tagService.findByName(name);
		}
		catch (FlashCardsException e)
		{
			throw new GenericWebServiceException(
					Response.Status.INTERNAL_SERVER_ERROR, e);
		}
		if (tagDto == null)
		{
			throw new GenericWebServiceException(Response.Status.NOT_FOUND,
					"Entity not found: " + name);
		}
		return tagDto;
	}

	@Override
	@PUT
	@Path("/{id}")
	@ApiOperation(value = "Replace a Tag")
	public Response put(@PathParam("id") final Long id, final TagDto dto)
	{
		if (dto.getCreatedBy() == null)
		{
			TagDto orig;
			try
			{
				orig = tagService.findOne(id);
			}
			catch (FlashCardsException e)
			{
				throw new GenericWebServiceException(
						Response.Status.INTERNAL_SERVER_ERROR, e);
			}
			dto.setFlashCards(orig.getFlashCards());
			dto.setCreatedBy(orig.getCreatedBy());
			dto.setCreatedDate(orig.getCreatedDate());
		}
		return super.put(id, dto);
	}

	@Override
	public JResponse<List<TagDto>> list(final Integer page, final Integer size,
			final String sort, final String direction,
			final String fields)
	{
		List<TagDto> entities;

		try
		{
			entities = getService().findAll(PagingUtils.getPageRequest(page, size, sort, direction), getFieldsAsSet(fields));
		}
		catch (final InvalidDataAccessApiUsageException e)
		{
			LOGGER.error(e.getMessage(), e);
			throw new GenericWebServiceException(Response.Status.BAD_REQUEST,
					"Invalid sort parameter: '" + sort + "'", e);
		}
		catch (final FlashCardsException e)
		{
			throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
		}

		if (CollectionUtils.isEmpty(entities))
		{
			entities = new ArrayList<>();
		}

		return JResponse.ok(entities).build();
	}

	@GET
	public JResponse<List<TagDto>> list(@PathParam("flashcardId") final Long flashCardId,
			@PathParam("userId") final Long userId,
			@QueryParam("page") final Integer page,
			@DefaultValue("25") @QueryParam("size") final Integer size,
			@QueryParam("sort") final String sort,
			@DefaultValue("asc") @QueryParam("direction") final String direction,
			@QueryParam("fields") final String fields)
	{

		if (flashCardId != null)
		{
			return listTagsForFlashCard(flashCardId, page, size, sort, direction, fields);
		}
		else if (userId != null)
		{
			return listTagsForUser(userId, page, size, sort, direction, fields);
		}
		else
		{
			return list(page, size, sort, direction, fields);
		}
	}

	private JResponse<List<TagDto>> listTagsForFlashCard(final Long flashCardId, final Integer page,
			final Integer size, final String sort,
			final String direction, final String fields)
	{
		try
		{
			final List<TagDto> entities = tagService.findTagsForFlashCard(flashCardId, this.getFieldsAsSet(fields));
			if (CollectionUtils.isEmpty(entities))
			{
				throw new GenericWebServiceException(Response.Status.NOT_FOUND,
						"Tags not found for FlashCard: " + flashCardId);
			}
			return JResponse.ok(entities).build();
		}
		catch (final FlashCardsException e)
		{
			throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
		}
	}

	private JResponse<List<TagDto>> listTagsForUser(final Long userId, final Integer page,
			final Integer size, final String sort,
			final String direction, final String fields)
	{
		try
		{
			final List<TagDto> entities = tagService.findByCreatedBy(userId, this.getFieldsAsSet(fields));
			if (CollectionUtils.isEmpty(entities))
			{
				throw new GenericWebServiceException(Response.Status.NOT_FOUND,
						"Tags not found for User: " + userId);
			}
			return JResponse.ok(entities).build();
		}
		catch (final FlashCardsException e)
		{
			throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
		}
	}

	@Path("/{tagId}/flashcards")
	public Class findFlashCardsForTag()
	{
		return FlashCardsResource.class;
	}
}
