
package org.robbins.flashcards.cassandra.repository;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.Session;
import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagFlashCardCassandraEntity;
import org.robbins.flashcards.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

@Repository
public class TagRepositoryImpl extends AbstractCrudRepositoryImpl<TagCassandraEntity, UUID> implements
        TagRepository<TagCassandraEntity, UUID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagRepositoryImpl.class);

    @Inject
    private CassandraOperations cassandraOperations;

    @Inject
    private TagCassandraRepository repository;

    @Inject
    private TagFlashcardCassandraRepository tagFlashcardCassandraRepository;

    @Inject
    FlashCardCassandraRepository flashCardCassandraRepository;

    private PreparedStatement tagStatement;
    private PreparedStatement flashcardStatement;

    private static final String TAG_TABLE = "tag";
    private static final String FLASHCARD_TABLE = "flashcard";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TAGS = "tags";

    @Override
    public TagCassandraRepository getRepository() {
        return repository;
    }

    @SuppressWarnings("unused")
    @PostConstruct
    private void initStatements() {
        Session session = cassandraOperations.getSession();
        if (session == null) {
            LOGGER.error("Cassandra not available");
        } else {
            tagStatement = session.prepare(tagInsert());
            flashcardStatement = session.prepare(flashcardUpdateTag());
        }

    }

    @Override
    public TagCassandraEntity save(final TagCassandraEntity tag) {
        cassandraOperations.execute(tagBatch(tag));

        return tag;
    }

    private BatchStatement tagBatch(TagCassandraEntity tag) {
        BatchStatement batch = new BatchStatement();
        batch.add(tagStatement.bind(
                tag.getId(),
                tag.getName()));

        List<TagFlashCardCassandraEntity> tagFlashcards = tagFlashcardCassandraRepository.findByTagId(tag.getId());
        if (tagFlashcards != null && tagFlashcards.size() > 0) {
            for (TagFlashCardCassandraEntity tagFlashCard : tagFlashcards) {
                FlashCardCassandraEntity flashcard = flashCardCassandraRepository.findOne(tagFlashCard.getId().getFlashCardId());
                if (flashcard != null && flashcard.getTags() != null) {
                    batch.add(flashcardStatement.bind(
                            tag.getId(),
                            tag.getName(),
                            flashcard.getId()));
                }
            }
        }
        return batch;
    }

    private RegularStatement tagInsert() {
        return insertInto(TAG_TABLE)
                .value(ID, bindMarker())
                .value(NAME, bindMarker());
    }

    private RegularStatement flashcardUpdateTag() {
        return update(FLASHCARD_TABLE)
                .with(put(TAGS, bindMarker(), bindMarker()))
                .where(eq(ID, bindMarker()));
    }

    @Override
    public TagCassandraEntity findByName(final String name) {
        return repository.findByName(name);
    }

    @Override
    public List<TagCassandraEntity> findByFlashcards_Id(UUID flashcardId) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }
}
