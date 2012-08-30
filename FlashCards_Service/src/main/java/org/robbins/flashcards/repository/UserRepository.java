package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByOpenid(String openid);
}
