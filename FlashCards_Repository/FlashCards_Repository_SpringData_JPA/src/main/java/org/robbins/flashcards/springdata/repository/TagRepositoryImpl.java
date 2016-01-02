package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.batch.BatchSavingRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

import static org.robbins.flashcards.springdata.repository.predicates.TagPredicates.hasFlashCardId;
import static org.robbins.flashcards.springdata.repository.predicates.TagPredicates.hasName;

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
    @Cacheable("tagById")
    public Tag findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "tags", allEntries=true), @CacheEvict(value = "tagById", key = "#p0.id") })
    public Tag save(Tag entity) {
        return repository.save(entity);
    }

    @Override
    @Cacheable("tags")
    public List<Tag> findAll() {
        return repository.findAll();
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "tags", allEntries=true), @CacheEvict(value = "tagById", key = "#p0") })
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public int batchSave(final List<Tag> records)
    {
        return batchTagRepository.batchSave(records);
    }
}
