
package org.robbins.flashcards.cassandra.repository;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraDto;
import org.robbins.flashcards.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Repository
public class TagRepositoryImpl extends AbstractCrudRepositoryImpl<TagCassandraDto, UUID> implements
        TagRepository<TagCassandraDto, UUID> {

    @Inject
    private TagCassandraRepository repository;

    @Override
    public TagCassandraRepository getRepository() {
        return repository;
    }

    @Override
    public TagCassandraDto findByName(final String name) {
        return repository.findByName(name);
    }

    @Override
    public List<TagCassandraDto> findByFlashcards_Id(UUID flashcardId) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }
}
