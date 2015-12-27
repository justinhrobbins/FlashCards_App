package org.robbins.flashcards.dto.builder;

import org.robbins.flashcards.dto.UserDto;

public class UserDtoBuilder {

    private UserDto user = new UserDto();

    public UserDtoBuilder() {}

    public UserDtoBuilder withId(final Long id) {
        user.setId(id);
        return this;
    }

    public UserDtoBuilder withOpenId(final String openid) {
        user.setOpenid(openid);
        return this;
    }

    public UserDtoBuilder withEmail(final String email) {
        user.setEmail(email);
        return this;
    }

    public UserDto build() {
        return user;
    }

}
