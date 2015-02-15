
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSpringDataRepository extends JpaRepository<User, Long> {

    User findUserByOpenid(String openid);
}
