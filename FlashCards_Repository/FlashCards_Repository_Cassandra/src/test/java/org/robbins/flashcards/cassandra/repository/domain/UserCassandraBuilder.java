package org.robbins.flashcards.cassandra.repository.domain;

import java.util.UUID;

public class UserCassandraBuilder {
    private final UserCassandraEntity user = new UserCassandraEntity();

    public UserCassandraBuilder withId(final UUID id) {
        this.user.setId(id);
        return this;
    }

    public UserCassandraBuilder withOpenId(final String openid) {
        this.user.setOpenid(openid);
        return this;
    }

    public UserCassandraEntity build()
    {
        return this.user;
    }
}