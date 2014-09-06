
package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.FlashCardsAppRepository;

public interface UserRepository extends FlashCardsAppRepository<User, Long> {

    User findUserByOpenid(String openid);
}
