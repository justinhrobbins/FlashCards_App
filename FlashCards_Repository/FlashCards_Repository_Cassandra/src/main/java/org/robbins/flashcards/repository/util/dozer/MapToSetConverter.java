package org.robbins.flashcards.repository.util.dozer;

import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.dto.TagDto;

import java.util.*;

public class MapToSetConverter extends DozerConverter<Map, Set> implements MapperAware {

    private Mapper mapper;

    public MapToSetConverter() {
        super(Map.class, Set.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set convertTo(Map source, Set destination) {

        if (source == null) return null;

        Set<TagDto> convertedSet = new HashSet<>();
        Set<Map.Entry> entries = source.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            TagCassandraEntity item = new TagCassandraEntity();
            item.setId((UUID) entry.getKey());
            item.setName((String) entry.getValue());
            TagDto mappedItem = mapper.map(item, TagDto.class);
            convertedSet.add(mappedItem);
        }
        return convertedSet;
    }

    @Override
    public Map convertFrom(Set source, Map destination) {
        if (source == null) return null;

        Map<UUID, String> originalToMapped = new HashMap<>();
        for (Object object : source) {
            TagDto item = (TagDto)object;
            TagCassandraEntity mappedItem = mapper.map(item, TagCassandraEntity.class);
            originalToMapped.put(mappedItem.getId(), mappedItem.getName());
        }
        return originalToMapped;
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
