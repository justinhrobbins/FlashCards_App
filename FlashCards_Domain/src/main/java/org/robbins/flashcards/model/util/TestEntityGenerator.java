package org.robbins.flashcards.model.util;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.model.User;

public class TestEntityGenerator {

	public static final Tag createTag(String name) {
		return new Tag(name);
	}
	
	public static final FlashCard createFlashCard(String question, String answer) {
		return new FlashCard(question, answer);
	}
	
	public static final User createUser(String openid, String email) {
		return new User(openid, email);
	}
}
