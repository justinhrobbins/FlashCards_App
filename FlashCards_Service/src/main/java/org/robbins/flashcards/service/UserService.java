
package org.robbins.flashcards.service;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;

public interface UserService extends GenericPagingAndSortingService<UserDto, Long>
{
    UserDto findUserByOpenid(String openid) throws FlashcardsException;
}
