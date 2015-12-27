
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Repository
public class FlashcardRepositoryImpl extends AbstractCrudRepositoryImpl<FlashCard, Long> implements
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
    @Cacheable("flashcardById")
    public FlashCard findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "flashcards", allEntries = true),
            @CacheEvict(value = "flashcardById", key = "#p0.id"),
            @CacheEvict(value = "tags", beforeInvocation=true, allEntries = true, condition = "!#entity.getTags().isEmpty()")})
    public FlashCard save(FlashCard entity) {
        return repository.save(entity);
    }

    @Override
    @Cacheable("flashcards")
    public List<FlashCard> findAll() {
        return repository.findAll();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "flashcards", allEntries = true),
            @CacheEvict(value = "flashcardById", key = "#p0"),
            @CacheEvict(value = "tags", allEntries = true)})
    public void delete(Long id) {
        repository.delete(id);
    }


}
