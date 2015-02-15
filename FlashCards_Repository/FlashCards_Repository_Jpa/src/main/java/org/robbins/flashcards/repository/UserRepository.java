
package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.User;

public interface UserRepository extends FlashCardsAppRepository<User, Long> {

    User findUserByOpenid(String openid);
}
