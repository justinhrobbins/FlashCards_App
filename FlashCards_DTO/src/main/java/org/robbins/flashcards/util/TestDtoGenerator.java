
package org.robbins.flashcards.util;

import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.UserDto;

public final class TestDtoGenerator {

    private TestDtoGenerator() {};

    public static TagDto createTagDto(final String name) {
        return new TagDto(name);
    }

    public static UserDto createUserDto(final String openid, final String email) {
        return new UserDto(openid, email);
    }

    public static FlashCardDto createFlashCardDto(final String question,
            final String answer) {
        return new FlashCardDto(question, answer);
    }
}
