package org.robbins.flashcards.repository.domain;

import java.util.UUID;

public class TagCassandraBuilder {
    private TagCassandra tag = new TagCassandra();

    public TagCassandraBuilder withId(UUID id) {
        this.tag.setId(id);
        return this;
    }

    public TagCassandraBuilder withName(String name) {
        this.tag.setName(name);
        return this;
    }

    public TagCassandra build()
    {
        return this.tag;
    }
}