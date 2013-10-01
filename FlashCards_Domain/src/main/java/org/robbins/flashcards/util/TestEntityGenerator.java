package org.robbins.flashcards.util;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.model.User;

public final class TestEntityGenerator {

	private TestEntityGenerator(){};
	
	public static Tag createTag(String name) {
		return new Tag(name);
	}

	public static TagDto createTagDto(String name) {
		return new TagDto(name);
	}
	
	public static FlashCard createFlashCard(String question, String answer) {
		return new FlashCard(question, answer);
	}
	
	public static User createUser(String openid, String email) {
		return new User(openid, email);
	}

	public static UserDto createUserDto(String openid, String email) {
		return new UserDto(openid, email);
	}
	
	public static FlashCardDto createFlashCardDto(String question, String answer) {
		return new FlashCardDto(question, answer);
	}
}
