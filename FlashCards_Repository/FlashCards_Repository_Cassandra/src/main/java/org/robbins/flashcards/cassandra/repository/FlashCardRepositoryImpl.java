
package org.robbins.flashcards.cassandra.repository;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraDto;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraDto;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.robbins.flashcards.repository.TagRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public class FlashCardRepositoryImpl extends AbstractCrudRepositoryImpl<FlashCardCassandraDto, UUID> implements
        FlashCardRepository<FlashCardCassandraDto, TagCassandraDto, UUID> {

    @Inject
    private FlashCardCassandraRepository repository;

    @Override
    public FlashCardCassandraRepository getRepository() {
        return repository;
    }

    @Override
    public List<FlashCardCassandraDto> findByTagsIn(Set<TagCassandraDto> tags) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardCassandraDto> findByTagsIn(Set<TagCassandraDto> tags, PageRequest page) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardCassandraDto> findByQuestionLike(String question) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardCassandraDto> findByQuestionLike(String question, PageRequest page) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public FlashCardCassandraDto findByQuestion(String question) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardCassandraDto> findByTags_Id(UUID tagId) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }
}
