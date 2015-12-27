package org.robbins.flashcards.repository.util.dozer;

import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.robbins.flashcards.cassandra.repository.domain.AbstractPersistable;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.dto.TagDto;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class MapToSetConverter extends DozerConverter<Map, Set> implements MapperAware {

    private Mapper mapper;

    public MapToSetConverter() {
        super(Map.class, Set.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set convertTo(Map source, Set destination) {

        if (source == null) return null;

        final Set<TagDto> convertedSet = new HashSet<>();
        final Set<Map.Entry> entries = source.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            TagCassandraEntity item = new TagCassandraEntity();
            item.setId((Long) entry.getKey());
            item.setName((String) entry.getValue());
            TagDto mappedItem = mapper.map(item, TagDto.class);
            convertedSet.add(mappedItem);
        }
        return convertedSet;
    }

    @Override
    public Map convertFrom(Set source, Map destination) {
        if (source == null) return null;

         return ((Set<TagDto>) source).stream()
                .map(this::convertDtoToEntity)
                .collect(Collectors.toMap(AbstractPersistable::getId, (tag) -> tag, (tag1, tag2) -> tag1));
    }

    private TagCassandraEntity convertDtoToEntity(TagDto tagDto) {
        return mapper.map(tagDto, TagCassandraEntity.class);
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
