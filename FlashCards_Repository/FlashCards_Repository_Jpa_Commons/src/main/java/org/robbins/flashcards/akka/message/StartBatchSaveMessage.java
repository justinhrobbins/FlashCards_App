package org.robbins.flashcards.akka.message;

import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.AbstractPersistableDto;
import org.robbins.flashcards.repository.FlashCardsAppRepository;

import java.io.Serializable;
import java.util.List;

public class StartBatchSaveMessage implements Serializable {
    private final FlashCardsAppRepository repository;
    private final DtoConverter converter;
    private final String auditingUserId;
    private final List<AbstractPersistableDto> dtos;

    public StartBatchSaveMessage(final FlashCardsAppRepository repository, final DtoConverter converter, final String auditingUserId, final List<AbstractPersistableDto> dtos) {
        this.repository = repository;
        this.converter = converter;
        this.auditingUserId = auditingUserId;
        this.dtos = dtos;
    }

    public FlashCardsAppRepository getRepository() {
        return repository;
    }

    public List<AbstractPersistableDto> getDtos() {
        return dtos;
    }

    public DtoConverter getConverter() {
        return converter;
    }

    public String getAuditingUserId() {
        return auditingUserId;
    }

    @Override
    public String toString() {
        return "StartBatchSaveMessage{" +
                "repository=" + repository +
                ", converter=" + converter +
                ", auditingUserId='" + auditingUserId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StartBatchSaveMessage that = (StartBatchSaveMessage) o;

        if (!repository.equals(that.repository)) return false;
        if (!converter.equals(that.converter)) return false;
        if (!auditingUserId.equals(that.auditingUserId)) return false;
        return dtos.equals(that.dtos);

    }

    @Override
    public int hashCode() {
        int result = repository.hashCode();
        result = 31 * result + converter.hashCode();
        result = 31 * result + auditingUserId.hashCode();
        result = 31 * result + dtos.hashCode();
        return result;
    }
}