
package org.robbins.flashcards.service.jpa;

import javax.inject.Inject;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.jpa.TagRepository;
import org.robbins.flashcards.service.TagService;
import org.robbins.flashcards.service.jpa.base.AbstractCrudServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TagServiceImpl extends AbstractCrudServiceImpl<Tag> implements TagService {

    @Inject
    private TagRepository repository;

    @Override
    public TagRepository getRepository() {
        return repository;
    }

    @Override
    public Tag findByName(final String name) {
        return getRepository().findByName(name);
    }
}
