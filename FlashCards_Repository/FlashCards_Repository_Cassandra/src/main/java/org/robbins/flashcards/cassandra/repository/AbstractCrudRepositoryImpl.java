
package org.robbins.flashcards.cassandra.repository;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractCrudRepositoryImpl<T, ID extends Serializable>
        implements FlashCardsAppRepository<T, ID> {

    public abstract TypedIdCassandraRepository<T, ID> getRepository();

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
        return Lists.newArrayList(getRepository().findAll());
    }

    @Override
    public List<T> findAll(final Sort sort) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public Page<T> findAll(final Pageable page) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<T> findByCreatedBy_Id(final ID userId) {
        return findByCreatedBy_Id(userId);
    }


}
