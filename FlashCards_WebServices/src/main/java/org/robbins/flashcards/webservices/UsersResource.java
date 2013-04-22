package org.robbins.flashcards.webservices;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.UserService;
import org.robbins.flashcards.service.base.GenericJpaService;
import org.robbins.flashcards.webservices.base.AbstractGenericResource;
import org.springframework.stereotype.Component;

@Path("/users/")
@Component("usersResource")
@Produces("application/json")
public class UsersResource extends AbstractGenericResource<User, Long> {
	
	@Inject
	private UserService service;

	protected GenericJpaService<User, Long> getService() {
		return service;
	}
	
	@GET
	@Path("/search")
	@Produces("application/json")
	public User getUserSearch(@QueryParam("openid") String openid) {
			return service.findUserByOpenid(openid);
	}
	
	@Override
	public Response put(@PathParam("id") Long id, User entity) {

		if (entity.getCreatedBy() == null) {
			User orig = service.findOne(id);
			entity.setCreatedBy(orig.getCreatedBy());
			entity.setCreatedDate(orig.getCreatedDate());
		}
		return super.put(id,  entity);
	}
}
