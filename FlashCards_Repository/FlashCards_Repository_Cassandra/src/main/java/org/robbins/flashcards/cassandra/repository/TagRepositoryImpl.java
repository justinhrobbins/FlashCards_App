
package org.robbins.flashcards.cassandra.repository;

import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;
import static com.datastax.driver.core.querybuilder.QueryBuilder.put;
import static com.datastax.driver.core.querybuilder.QueryBuilder.update;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.domain.FlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagFlashCardCassandraEntity;
import org.robbins.flashcards.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.Session;

@Repository
public class TagRepositoryImpl extends AbstractCrudRepositoryImpl<TagCassandraEntity, Long> implements
        TagRepository<TagCassandraEntity, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagRepositoryImpl.class);

    private static final String TAG_TABLE = "tag";
    private static final String FLASHCARD_TABLE = "flashcard";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TAGS = "tags";
    private static final String BATCH_INSERT_CQL = "insert into tag (id, name) values (?, ?);";

    @Inject
    private CassandraOperations cassandraTemplate;

    @Inject
    private TagCassandraRepository repository;

    @Inject
    private TagFlashcardCassandraRepository tagFlashcardCassandraRepository;

    @Inject
    FlashCardCassandraRepository flashCardCassandraRepository;

    private PreparedStatement tagStatement;
    private PreparedStatement flashcardStatement;

    @Override
    public TagCassandraRepository getRepository() {
        return repository;
    }

    @SuppressWarnings("unused")
    @PostConstruct
    private void initStatements() {
        final Session session = cassandraTemplate.getSession();
        if (session == null) {
            LOGGER.error("Cassandra not available");
        } else {
            tagStatement = session.prepare(tagInsert());
            flashcardStatement = session.prepare(flashcardUpdateTag());
        }

    }

    @Override
    public TagCassandraEntity save(final TagCassandraEntity tag) {
        cassandraTemplate.execute(tagBatch(tag));

        return tag;
    }

    private BatchStatement tagBatch(TagCassandraEntity tag) {
        final BatchStatement batch = new BatchStatement();
        batch.add(tagStatement.bind(
                tag.getId(),
                tag.getName()));

        final List<TagFlashCardCassandraEntity> tagFlashcards = tagFlashcardCassandraRepository.findByTagId(tag.getId());
        if (tagFlashcards != null && tagFlashcards.size() > 0) {
            for (TagFlashCardCassandraEntity tagFlashCard : tagFlashcards) {
                final FlashCardCassandraEntity flashcard = flashCardCassandraRepository.findOne(tagFlashCard.getId().getFlashCardId());
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
    public List<TagCassandraEntity> findByFlashcards_Id(Long flashcardId) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public int batchSave(final List<TagCassandraEntity> tags)
    {
        cassandraTemplate.ingest(BATCH_INSERT_CQL, convertTagsForIngestion(tags));
        return tags.size();
    }

    private List<List<?>> convertTagsForIngestion(final List<TagCassandraEntity> tags)
    {
        return tags
                .stream()
                .map(this::convertTagForIngestion)
                .collect(Collectors.toList());
    }

    private List<?> convertTagForIngestion(final TagCassandraEntity tag)
    {
        final List<Object> tagList = new ArrayList<>();
        tagList.add(tag.getId());
        tagList.add(tag.getName());
        return tagList;
    }
}
