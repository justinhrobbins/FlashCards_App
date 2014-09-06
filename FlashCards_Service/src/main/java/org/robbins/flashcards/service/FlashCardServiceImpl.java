
package org.robbins.flashcards.service;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.robbins.flashcards.service.base.AbstractCrudServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FlashCardServiceImpl extends AbstractCrudServiceImpl<FlashCard>
        implements FlashCardService {

    @Inject
    private FlashCardRepository repository;

    @Override
    public FlashCardRepository getRepository() {
        return repository;
    }

    @Override
    public List<FlashCard> findByTagsIn(final Set<Tag> tags) {
        return getRepository().findByTagsIn(tags);
    }

    @Override
    public List<FlashCard> findByTagsIn(final Set<Tag> tags, final PageRequest page) {
        return getRepository().findByTagsIn(tags, page);
    }

    @Override
    public List<FlashCard> findByQuestionLike(final String question) {
        return getRepository().findByQuestionLike(question);
    }

    @Override
    public List<FlashCard> findByQuestionLike(final String question,
            final PageRequest page) {
        return getRepository().findByQuestionLike(question, page);
    }

    @Override
    public FlashCard findByQuestion(final String question) {
        return getRepository().findByQuestion(question);
    }
}
