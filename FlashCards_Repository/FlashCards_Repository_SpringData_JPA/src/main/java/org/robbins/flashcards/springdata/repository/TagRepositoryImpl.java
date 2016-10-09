package org.robbins.flashcards.springdata.repository;

import static org.robbins.flashcards.springdata.repository.predicates.TagPredicates.hasFlashCardId;
import static org.robbins.flashcards.springdata.repository.predicates.TagPredicates.hasName;

import java.util.List;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.inject.Inject;

import org.robbins.flashcards.caching.PersistableCacheKeyGenerator;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.batch.BatchSavingRepository;
import org.springframework.stereotype.Repository;

@CacheDefaults(cacheName = "tagById", cacheKeyGenerator = PersistableCacheKeyGenerator.class)
@Repository
public class TagRepositoryImpl extends AbstractCrudRepositoryImpl<Tag, Long> implements
        TagRepository<Tag, Long>, BatchSavingRepository<Tag>
{
    @Inject
    private TagSpringDataRepository repository;

    @Inject
    private BatchSavingRepository<Tag> batchTagRepository;

    @Override
    public TagSpringDataRepository getRepository() {
        return repository;
    }

    @Override
    public Tag findByName(final String name) {
        return repository.findOne(hasName(name));
    }

    @Override
    public List<Tag> findByFlashCards_Id(final Long flashCardId) {
        return toList(repository.findAll(hasFlashCardId(flashCardId)));
    }

    @Override
    @CacheResult
    public Tag findOne(final Long id) {
        return repository.findOne(id);
    }

    @Override
    @CacheRemove
    public Tag save(final Tag entity) {
        return repository.save(entity);
    }

    @Override
    public List<Tag> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(final Long id) {
        repository.delete(id);
    }

    @Override
    public int batchSave(final List<Tag> records)
    {
        return batchTagRepository.batchSave(records);
    }
}
