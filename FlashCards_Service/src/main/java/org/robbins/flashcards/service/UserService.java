
package org.robbins.flashcards.service;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;

public interface UserService extends GenericPagingAndSortingService<UserDto, Long>
{
    UserDto findUserByOpenid(final String openid) throws FlashCardsException;
}
