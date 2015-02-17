
package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.User;

public interface UserRepository extends FlashCardsAppRepository<User, String> {

    User findUserByOpenid(String openid);
}
