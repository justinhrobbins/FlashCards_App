package org.robbins.flashcards.cassandra.repository.domain;

import java.util.UUID;

public class UserCassandraBuilder {
    private final UserCassandraDto user = new UserCassandraDto();

    public UserCassandraBuilder withId(final UUID id) {
        this.user.setId(id);
        return this;
    }

    public UserCassandraBuilder withOpenId(final String openid) {
        this.user.setOpenid(openid);
        return this;
    }

    public UserCassandraDto build()
    {
        return this.user;
    }
}