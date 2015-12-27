package org.robbins.flashcards.akka.message;

import org.robbins.flashcards.dto.AbstractPersistableDto;

import java.io.Serializable;

public class SingleSaveStartMessage implements Serializable {
    private final AbstractPersistableDto dto;

    public SingleSaveStartMessage(AbstractPersistableDto dto) {
        this.dto = dto;
    }

    public AbstractPersistableDto getDto() {
        return dto;
    }

    @Override
    public String toString() {
        return "SingleSaveStart{" +
                "dto=" + dto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleSaveStartMessage that = (SingleSaveStartMessage) o;

        return dto.equals(that.dto);

    }

    @Override
    public int hashCode() {
        return dto.hashCode();
    }
}