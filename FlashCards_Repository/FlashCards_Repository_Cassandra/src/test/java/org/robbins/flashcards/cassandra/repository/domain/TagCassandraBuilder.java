package org.robbins.flashcards.cassandra.repository.domain;

import java.util.UUID;

public class TagCassandraBuilder {
    private final TagCassandraDto tag = new TagCassandraDto();

    public TagCassandraBuilder withId(final UUID id) {
        this.tag.setId(id);
        return this;
    }

    public TagCassandraBuilder withName(final String name) {
        this.tag.setName(name);
        return this;
    }

    public TagCassandraDto build()
    {
        return this.tag;
    }
}