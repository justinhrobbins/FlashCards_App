
package org.robbins.flashcards.service;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.service.base.GenericJpaService;

public interface UserService extends GenericJpaService<User, Long> {

    User findUserByOpenid(String openid);
}
