
package org.robbins.flashcards.service.springdata;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.springdata.FlashCardRepository;
import org.robbins.flashcards.service.FlashCardService;
import org.robbins.flashcards.service.springdata.base.GenericJpaServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FlashCardServiceImpl extends GenericJpaServiceImpl<FlashCard, Long>
        implements FlashCardService {

    @Inject
    private FlashCardRepository repository;

    @Override
    protected FlashCardRepository getRepository() {
        return repository;
    }

    @Override
    public List<FlashCard> findByTagsIn(Set<Tag> tags) {
        return getRepository().findByTagsIn(tags);
    }

    @Override
    public List<FlashCard> findByTagsIn(Set<Tag> tags, PageRequest page) {
        return getRepository().findByTagsIn(tags, page);
    }

    @Override
    public List<FlashCard> findByQuestionLike(String question) {
        return getRepository().findByQuestionLike(question);
    }

    @Override
    public List<FlashCard> findByQuestionLike(String question, PageRequest page) {
        return getRepository().findByQuestionLike(question, page);
    }

    @Override
    public FlashCard findByQuestion(String question) {
        return getRepository().findByQuestion(question);
    }
}
