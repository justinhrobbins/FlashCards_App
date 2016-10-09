
package org.robbins.flashcards.springdata.repository;

import java.util.List;
import java.util.Set;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.inject.Inject;

import org.robbins.flashcards.caching.PersistableCacheKeyGenerator;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@CacheDefaults(cacheName = "flashcardById", cacheKeyGenerator = PersistableCacheKeyGenerator.class)
@Repository
public class FlashCardRepositoryImpl extends AbstractCrudRepositoryImpl<FlashCard, Long> implements
        FlashCardRepository<FlashCard, Tag, Long> {

    @Inject
    private FlashCardSpringDataRepository repository;

    @Override
    public FlashCardSpringDataRepository getRepository() {
        return repository;
    }

    @Override
    public List<FlashCard> findByTagsIn(final Set<Tag> tags) {
        return repository.findByTagsIn(tags);
    }

    @Override
    public List<FlashCard> findByTagsIn(final Set<Tag> tags, final PageRequest page) {
        return repository.findByTagsIn(tags, page);
    }

    @Override
    public List<FlashCard> findByQuestionLike(final String question) {
        return repository.findByQuestionLike(question);
    }

    @Override
    public List<FlashCard> findByQuestionLike(final String question, final PageRequest page) {
        return repository.findByQuestionLike(question, page);
    }

    @Override
    public FlashCard findByQuestion(final String question) {
        return repository.findByQuestion(question);
    }

    @Override
    public List<FlashCard> findByTags_Id(final Long tagId) {
        return repository.findByTags_Id(tagId);
    }

    @Override
    @CacheResult
    public FlashCard findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    @CacheRemove
    public FlashCard save(FlashCard entity) {
        return repository.save(entity);
    }

    @Override
    public List<FlashCard> findAll() {
        return repository.findAll();
    }

    @Override
    @CacheRemove
    public void delete(Long id) {
        repository.delete(id);
    }


}
