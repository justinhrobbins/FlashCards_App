
package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractCrudRepositoryImpl<T extends AbstractAuditable<User, Long>>
        implements FlashCardsAppRepository<T, Long> {

    public abstract JpaRepository<T, Long> getRepository();

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    public T save(final T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T findOne(final Long id) {
        return getRepository().findOne(id);
    }

    @Override
    public void delete(final Long id) {
        getRepository().delete(id);
    }

    @Override
    public void delete(final T dto) {
        getRepository().delete(dto);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public List<T> findAll(final Sort sort) {
        return getRepository().findAll(sort);
    }

    @Override
    public Page<T> findAll(final Pageable page) {
        return getRepository().findAll(page);
    }

    @Override
    public List<T> findByCreatedBy_Id(final Long userId) {
        return findByCreatedBy_Id(userId);
    }
}
