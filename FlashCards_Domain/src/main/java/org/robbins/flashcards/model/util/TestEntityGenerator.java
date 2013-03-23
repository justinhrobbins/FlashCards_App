package org.robbins.flashcards.model.util;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.model.User;

public final class TestEntityGenerator {

	private TestEntityGenerator(){};
	
	public static Tag createTag(String name) {
		return new Tag(name);
	}
	
	public static FlashCard createFlashCard(String question, String answer) {
		return new FlashCard(question, answer);
	}
	
	public static User createUser(String openid, String email) {
		return new User(openid, email);
	}
}
