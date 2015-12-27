package org.robbins.flashcards.cassandra.repository.domain;

public class TagCassandraBuilder {
    private final TagCassandraEntity tag = new TagCassandraEntity();

    public TagCassandraBuilder withId(final Long id) {
        this.tag.setId(id);
        return this;
    }

    public TagCassandraBuilder withName(final String name) {
        this.tag.setName(name);
        return this;
    }

    public TagCassandraEntity build()
    {
        return this.tag;
    }
}