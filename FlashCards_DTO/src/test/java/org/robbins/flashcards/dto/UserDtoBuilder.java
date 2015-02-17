package org.robbins.flashcards.dto;

/**
 * Created by justinrobbins on 2/16/15.
 */
public class UserDtoBuilder {

    private UserDto user = new UserDto();

    public UserDtoBuilder() {}

    public UserDtoBuilder withId(final String id) {
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
