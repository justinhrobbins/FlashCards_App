
package org.robbins.flashcards.springdata.repository;

import com.google.common.collect.Lists;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractCrudRepositoryImpl<T extends AbstractAuditable<String, ID>, ID extends Serializable>
        implements FlashCardsAppRepository<T, ID> {

    public abstract JpaRepository<T, ID> getRepository();

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    public T save(final T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T findOne(final ID id) {
        return getRepository().findOne(id);
    }

    @Override
    public void delete(final ID id) {
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
    public List<T> findByCreatedBy_Id(final ID userId) {
        return findByCreatedBy_Id(userId);
    }

    List<T> toList(Iterable<T> iterable) {
        return Lists.newArrayList(iterable);
    }
}
