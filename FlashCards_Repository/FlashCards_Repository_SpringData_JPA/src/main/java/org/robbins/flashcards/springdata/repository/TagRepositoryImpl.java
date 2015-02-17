
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class TagRepositoryImpl extends AbstractCrudRepositoryImpl<Tag, String> implements
        TagRepository {

    @Inject
    private TagSpringDataRepository repository;

    @Override
    public TagSpringDataRepository getRepository() {
        return repository;
    }

    @Override
    public Tag findByName(final String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Tag> findByFlashcards_Id(final String flashcardId) {
        return repository.findByFlashcards_Id(flashcardId);
    }
}
