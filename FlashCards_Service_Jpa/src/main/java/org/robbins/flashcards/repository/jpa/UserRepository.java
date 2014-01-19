
package org.robbins.flashcards.repository.jpa;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.jpa.base.CrudRepository;

public interface UserRepository extends CrudRepository<User> {

    User findUserByOpenid(String openid);
}
