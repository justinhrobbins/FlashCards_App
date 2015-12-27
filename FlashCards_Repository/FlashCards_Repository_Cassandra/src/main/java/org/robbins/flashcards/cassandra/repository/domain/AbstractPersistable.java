package org.robbins.flashcards.cassandra.repository.domain;

import org.springframework.data.cassandra.mapping.PrimaryKey;

import java.util.UUID;

public class AbstractPersistable {
    @PrimaryKey
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
