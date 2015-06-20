
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserSpringDataRepository extends JpaRepository<User, String>, QueryDslPredicateExecutor<User> {

    User findUserByOpenid(String openid);
}
