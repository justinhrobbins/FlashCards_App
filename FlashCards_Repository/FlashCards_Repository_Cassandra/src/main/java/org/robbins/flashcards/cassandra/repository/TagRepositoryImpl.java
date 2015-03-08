
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
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

@Repository
public class TagRepositoryImpl extends AbstractCrudRepositoryImpl<TagCassandraEntity, UUID> implements
        TagRepository<TagCassandraEntity, UUID> {

    @Inject
    private CassandraOperations cassandraOperations;

    @Inject
    private TagCassandraRepository repository;

    @Inject
    private TagFlashcardCassandraRepository tagFlashcardCassandraRepository;

    @Inject FlashCardCassandraRepository flashCardCassandraRepository;

    private static final String TAG_TABLE = "tag";
    private static final String FLASHCARD_TABLE = "flashcard";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TAGS = "tags";

    @Override
    public TagCassandraRepository getRepository() {
        return repository;
    }

    @Override
    public TagCassandraEntity save(final TagCassandraEntity tag) {
        cassandraOperations.execute(tagBatch(tag));

        return tag;
    }

    private void updateFlashCardTags(final TagCassandraEntity tag) {
        List<TagFlashCardCassandraEntity> tagFlashcards = tagFlashcardCassandraRepository.findByTagId(tag.getId());
        if (tagFlashcards != null && tagFlashcards.size() > 0) {
            for (TagFlashCardCassandraEntity tagFlashCard : tagFlashcards) {
                FlashCardCassandraEntity flashcard = flashCardCassandraRepository.findOne(tagFlashCard.getId().getFlashCardId());
                if (flashcard != null && flashcard.getTags() != null) {
                    flashcard.getTags().put(tag.getId(), tag.getName());
                }
            }
        }
    }

    private BatchStatement tagBatch(TagCassandraEntity tag) {
        Session session = cassandraOperations.getSession();

        PreparedStatement tagStatement = session.prepare(tagInsert());
        PreparedStatement flashcardStatement = session.prepare(flashcardUpdateTag());

        BatchStatement batch = new BatchStatement();

        // flashcard
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
