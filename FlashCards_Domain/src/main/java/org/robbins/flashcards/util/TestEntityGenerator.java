
package org.robbins.flashcards.util;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.model.User;

public final class TestEntityGenerator {

    private TestEntityGenerator() {
    };

    public static Tag createTag(final String name) {
        return new Tag(name);
    }

    public static TagDto createTagDto(final String name) {
        return new TagDto(name);
    }

    public static FlashCard createFlashCard(final String question, final String answer) {
        return new FlashCard(question, answer);
    }

    public static User createUser(final String openid, final String email) {
        return new User(openid, email);
    }

    public static UserDto createUserDto(final String openid, final String email) {
        return new UserDto(openid, email);
    }

    public static FlashCardDto createFlashCardDto(final String question,
            final String answer) {
        return new FlashCardDto(question, answer);
    }
}
