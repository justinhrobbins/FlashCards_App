/**
 * Copyright (C) 2009-2011 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.robbins.flashcards.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import org.robbins.flashcards.model.User;


@Path("/flashcardsapi/v1/users")
public interface UserRestService extends RestService {

    @GET
    @Path("/flashcardsapi/v1/users")
    @Consumes("application/json")
    @Produces("application/json")
    public void getUsers(	@HeaderParam("Authorization") String authHeader, 
    						MethodCallback<List<User>> callback);
    
    @GET
    @Path("/flashcardsapi/v1/users/search")
    @Consumes("application/json")
    @Produces("application/json")
    @Options(expect={200,204,1223})
    public void getUsersSearch(	@HeaderParam("Authorization") String authHeader, 
    							@QueryParam("openid") String openid, 
    							MethodCallback<User> callback);

    @GET
    @Path("/flashcardsapi/v1/users/{userId}")
    @Consumes("application/json")
    @Produces("application/json")
    public void getUser(@HeaderParam("Authorization") String authHeader, 
    					@PathParam("userId") Long userId, 
    					MethodCallback<User> callback);
    
    @POST
    @Path("/flashcardsapi/v1/users")
    @Consumes("application/json")
    @Produces("application/json")
    public void postUsers(	@HeaderParam("Authorization") String authHeader, 
    						User user, 
    						MethodCallback<User> callback);

    @PUT
    @Path("/flashcardsapi/v1/users/{userId}")
    @Consumes("application/json")
    @Produces("application/json")
    @Options(expect={204,1223})
    public void putUsers(	@HeaderParam("Authorization") String authHeader, 
    						@PathParam("userId") Long userId, 
    						MethodCallback<java.lang.Void> callback);
    
    @DELETE
    @Path("/flashcardsapi/v1/users/{userId}")
    @Consumes("application/json")
    @Produces("application/json")
    public void deleteUsers(@HeaderParam("Authorization") String authHeader, 
    						@PathParam("userId") Long userId, 
    						MethodCallback<java.lang.Void> callback);
}
