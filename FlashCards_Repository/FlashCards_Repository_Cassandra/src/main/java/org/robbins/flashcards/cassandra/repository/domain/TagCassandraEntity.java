package org.robbins.flashcards.cassandra.repository.domain;

import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;

@Table(value = "tag")
public class TagCassandraEntity extends AbstractPersistable implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
