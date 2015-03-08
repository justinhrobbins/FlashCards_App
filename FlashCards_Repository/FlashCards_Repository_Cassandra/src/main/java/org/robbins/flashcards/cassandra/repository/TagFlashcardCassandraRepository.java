package org.robbins.flashcards.cassandra.repository;

import org.robbins.flashcards.cassandra.repository.domain.TagFlashCardCassandraEntity;
import org.robbins.flashcards.cassandra.repository.domain.TagFlashCardKey;
import org.robbins.flashcards.cassandra.repository.domain.UserCassandraEntity;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagFlashcardCassandraRepository extends TypedIdCassandraRepository<TagFlashCardCassandraEntity, TagFlashCardKey> {

    @Query("SELECT * FROM tag_flashcard WHERE tag_id = ?0")
    public List<TagFlashCardCassandraEntity> findByTagId(final UUID tagId);
}
