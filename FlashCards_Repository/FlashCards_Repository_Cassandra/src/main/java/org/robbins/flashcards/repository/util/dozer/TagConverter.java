
package org.robbins.flashcards.repository.util.dozer;

import org.dozer.CustomConverter;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.dto.TagDto;
import org.springframework.data.mapping.model.MappingException;

import java.util.UUID;

public class TagConverter implements CustomConverter {

    @SuppressWarnings("rawtypes")
    @Override
    public Object convert(final Object existingDestinationFieldValue,
            final Object sourceFieldValue, final Class destinationClass,
            final Class sourceClass) {

        if (sourceFieldValue == null) {
            return null;
        }

        if (sourceFieldValue instanceof TagCassandraEntity) {
            TagCassandraEntity entity = (TagCassandraEntity)sourceFieldValue;
            TagDto dto = new TagDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            return dto;
        } else if (sourceFieldValue instanceof TagDto) {
            TagDto dto = (TagDto)sourceFieldValue;
            TagCassandraEntity entity = new TagCassandraEntity();
            if (dto.getId() != null) {
                entity.setId(dto.getId());
            }
            entity.setName(dto.getName());
            return entity;
        }

        throw new MappingException("Misconfigured/unsupported mapping");
    }
}
