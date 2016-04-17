
package org.robbins.flashcards.webservices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.service.UserService;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;
import org.robbins.flashcards.webservices.base.AbstractGenericListingResource;
import org.robbins.flashcards.webservices.exceptions.GenericWebServiceException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/users/")
@Component("usersResource")
@Api(value = "/v1/users", description = "Operations about Users")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class UsersResource extends AbstractGenericListingResource<UserDto, Long> {

    @Inject
    private UserService userService;

    @Override
    protected GenericPagingAndSortingService<UserDto, Long> getService() {
        return userService;
    }

    @GET
    @Path("/search")
    @ApiOperation(value = "Find a user by their OpenId", response = UserDto.class)
    public UserDto search(@QueryParam("openid") final String openid) {
        try {
            return userService.findUserByOpenid(openid);
        } catch (FlashCardsException e) {
            throw new GenericWebServiceException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Path("/{userId}/flashcards")
    public Class findFlashCardsForUser() {
        return FlashCardsResource.class;
    }

    @Path("/{userId}/tags")
    public Class findTagsForUser() {
        return TagsResource.class;
    }
}
