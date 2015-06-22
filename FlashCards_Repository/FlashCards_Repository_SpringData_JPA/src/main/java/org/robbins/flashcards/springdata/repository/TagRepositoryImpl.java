package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

import static org.robbins.flashcards.springdata.repository.predicates.TagPredicates.hasFlashcardId;
import static org.robbins.flashcards.springdata.repository.predicates.TagPredicates.hasName;

@Repository
public class TagRepositoryImpl extends AbstractCrudRepositoryImpl<Tag, String> implements
        TagRepository<Tag, String> {

    @Inject
    private TagSpringDataRepository repository;

    @Override
    public TagSpringDataRepository getRepository() {
        return repository;
    }

    @Override
    public Tag findByName(final String name) {
        return repository.findOne(hasName(name));
    }

    @Override
    public List<Tag> findByFlashcards_Id(final String flashcardId) {
        return toList(repository.findAll(hasFlashcardId(flashcardId)));
    }

    @Override
    @Cacheable("tagByTagId")
    public Tag findOne(String id) {
        return repository.findOne(id);
    }

    @Override
    @CacheEvict(value = "tagByTagId", key = "#p0.id")
    public Tag save(Tag entity) {
        return repository.save(entity);
    }
}
