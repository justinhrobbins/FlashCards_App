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
import org.robbins.flashcards.model.FlashCardDto;


@Path("/api/v1/flashcards")
public interface FlashCardRestService extends RestService {

    @GET
    @Path("api/v1/flashcards")
    @Consumes("application/json")
    @Produces("application/json")
    public void getFlashCards(	@HeaderParam("Authorization") String authHeader, 
    							@QueryParam("fields") String fields, 
    							MethodCallback<List<FlashCardDto>> callback);

    @GET
    @Path("/api/v1/flashcards/{flashCardId}")
    @Consumes("application/json")
    @Produces("application/json")
    public void getFlashCard(	@HeaderParam("Authorization") String authHeader, 
    							@PathParam("flashCardId") Long flashCardId, 
    							@QueryParam("fields") String fields, 
    							MethodCallback<FlashCardDto> callback);

    @GET
    @Path("/api/v1/flashcards/search")
    @Consumes("application/json")
    @Produces("application/json")
    @Options(expect={200,204,1223})
    public void getFlashCardsSearch(@HeaderParam("Authorization") String authHeader, 
    								@QueryParam("tagIds") String tagIds, 
    								MethodCallback<List<FlashCardDto>> callback);
    
    @POST
    @Path("/api/v1/flashcards")
    @Consumes("application/json")
    @Produces("application/json")
    public void postFlashCards(	@HeaderParam("Authorization") String authHeader, 
    							FlashCardDto flashCard, 
    							MethodCallback<FlashCardDto> callback);

    @PUT
    @Path("/api/v1/flashcards/{flashCardId}")
    @Consumes("application/json")
    @Produces("application/json")
    @Options(expect={204,1223})
    public void putFlashCard(	@HeaderParam("Authorization") String authHeader,
    							@PathParam("flashCardId") Long flashCardId,
    							FlashCardDto flashCard,
    							MethodCallback<java.lang.Void> callback);
    
    @DELETE
    @Path("/api/v1/flashcards/{flashCardId}")
    @Consumes("application/json")
    @Produces("application/json")
    public void deleteFlashCards(	@HeaderParam("Authorization") String authHeader, 
    								@PathParam("flashCardId") Long flashCardId, 
    								MethodCallback<java.lang.Void> callback);
}
