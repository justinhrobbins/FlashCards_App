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

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import org.robbins.flashcards.model.UserDto;

import javax.ws.rs.*;
import java.util.List;

@Path("/api/v1/users")
public interface UserRestService extends RestService {

    @GET
    @Path("/api/v1/users")
    @Consumes("application/json")
    @Produces("application/json")
    void getUsers(@HeaderParam("Authorization") String authHeader,
            MethodCallback<List<UserDto>> callback);

    @GET
    @Path("/api/v1/users/search")
    @Consumes("application/json")
    @Produces("application/json")
    @Options(expect = { 200, 204, 1223 })
    void getUsersSearch(@HeaderParam("Authorization") String authHeader,
            @QueryParam("openid") String openid, MethodCallback<UserDto> callback);

    @GET
    @Path("/api/v1/users/{userId}")
    @Consumes("application/json")
    @Produces("application/json")
    void getUser(@HeaderParam("Authorization") String authHeader,
            @PathParam("userId") String userId, MethodCallback<UserDto> callback);

    @POST
    @Path("/api/v1/users")
    @Consumes("application/json")
    @Produces("application/json")
    void postUsers(@HeaderParam("Authorization") String authHeader, UserDto user,
            MethodCallback<UserDto> callback);

    @PUT
    @Path("/api/v1/users/{userId}")
    @Consumes("application/json")
    @Produces("application/json")
    @Options(expect = { 204, 1223 })
    void putUsers(@HeaderParam("Authorization") String authHeader,
            @PathParam("userId") String userId, MethodCallback<java.lang.Void> callback);

    @DELETE
    @Path("/api/v1/users/{userId}")
    @Consumes("application/json")
    @Produces("application/json")
    void deleteUsers(@HeaderParam("Authorization") String authHeader,
            @PathParam("userId") String userId, MethodCallback<java.lang.Void> callback);
}
