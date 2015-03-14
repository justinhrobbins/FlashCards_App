
package org.robbins.flashcards.cassandra.repository;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.Session;
import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.robbins.flashcards.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;

@Repository
public class FlashCardRepositoryImpl extends AbstractCrudRepositoryImpl<FlashCardCassandraEntity, UUID> implements
        FlashCardRepository<FlashCardCassandraEntity, TagCassandraEntity, UUID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlashCardRepositoryImpl.class);

    @Inject
    private CassandraOperations cassandraOperations;

    @Inject
    private FlashCardCassandraRepository repository;

    @Inject
    private TagRepository<TagCassandraEntity, UUID> tagRepository;

    private PreparedStatement flashcardStatement;
    private PreparedStatement tagFlashcardStatement;

    private static final String TAG_FLASHCARD_TABLE = "tag_flashcard";
    private static final String FLASHCARD_TABLE = "flashcard";
    private static final String ID = "id";
    private static final String QUESTION = "question";
    private static final String ANSWER = "answer";
    private static final String TAGS = "tags";
    private static final String TAG_ID = "tag_id";
    private static final String FLASHCARD_ID = "flashcard_id";

    @Override
    public FlashCardCassandraRepository getRepository() {
        return repository;
    }

    @SuppressWarnings("unused")
    @PostConstruct
    private void initStatements(){
        Session session = cassandraOperations.getSession();
        if (session == null){
            LOGGER.error("Cassandra not available");
        } else {
            flashcardStatement = session.prepare(flashcardInsert());
            tagFlashcardStatement = session.prepare(tagFlashcardInsert());
        }

    }

    @Override
    public FlashCardCassandraEntity save(final FlashCardCassandraEntity flashcard) {
        cassandraOperations.execute(flashcardBatch(flashcard));

        return flashcard;
    }

    private BatchStatement flashcardBatch(FlashCardCassandraEntity flashcard) {
        BatchStatement batch = new BatchStatement();

        batch.add(flashcardStatement.bind(
                flashcard.getId(),
                flashcard.getQuestion(),
                flashcard.getAnswer(),
                flashcard.getTags()));

        for (Map.Entry<UUID, String> tagEntry : flashcard.getTags().entrySet()) {
            batch.add(tagFlashcardStatement.bind(
                    tagEntry.getKey(),
                    flashcard.getId(),
                    flashcard.getQuestion(),
                    flashcard.getAnswer()));
        }

        return batch;
    }

    private RegularStatement tagFlashcardInsert() {
        return insertInto(TAG_FLASHCARD_TABLE)
                .value(TAG_ID, bindMarker())
                .value(FLASHCARD_ID, bindMarker())
                .value(QUESTION, bindMarker())
                .value(ANSWER, bindMarker());
    }

    private RegularStatement flashcardInsert() {
        return insertInto(FLASHCARD_TABLE)
                .value(ID, bindMarker())
                .value(QUESTION, bindMarker())
                .value(ANSWER, bindMarker())
                .value(TAGS, bindMarker());
    }

    @Override
    public List<FlashCardCassandraEntity> findByTagsIn(Set<TagCassandraEntity> tags) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardCassandraEntity> findByTagsIn(Set<TagCassandraEntity> tags, PageRequest page) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardCassandraEntity> findByQuestionLike(String question) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardCassandraEntity> findByQuestionLike(String question, PageRequest page) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public FlashCardCassandraEntity findByQuestion(String question) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<FlashCardCassandraEntity> findByTags_Id(UUID tagId) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }
}
