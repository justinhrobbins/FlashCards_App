
package org.robbins.flashcards.service;

import javax.inject.Inject;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.service.base.AbstractCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends AbstractCrudServiceImpl<Tag> implements
        TagService {

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
