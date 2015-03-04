package org.robbins.flashcards.cassandra.repository.domain;

import org.springframework.data.cassandra.mapping.PrimaryKey;

import java.util.UUID;

public class AbstractPersistable {
    @PrimaryKey
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
