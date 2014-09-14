package org.robbins.flashcards.client;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.facade.UserFacade;

public interface UserClient extends UserFacade, GenericRestCrudFacade<UserDto> {
}
