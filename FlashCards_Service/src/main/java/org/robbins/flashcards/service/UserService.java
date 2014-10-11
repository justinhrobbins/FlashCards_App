
package org.robbins.flashcards.service;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.base.GenericPagingAndSortingService;

public interface UserService extends GenericPagingAndSortingService<User, Long>
{

    User findUserByOpenid(String openid);
}
