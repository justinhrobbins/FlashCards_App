
package org.robbins.flashcards.jpa.repository;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.robbins.flashcards.repository.auditing.AuditingAwareUser;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractCrudRepositoryImpl<T extends AbstractAuditable<Long, ID>, ID extends Serializable>
        implements FlashCardsAppRepository<T, ID> {

    @PersistenceContext
    private EntityManager em;

    public abstract Class<T> getClazz();

    @Inject
    private AuditingAwareUser auditorAware;

    public Long getAuditingUser() {
        return auditorAware.getCurrentAuditor();
    }

    public EntityManager getEm() {
        return em;
    }

    @Transactional
    @Override
    public T save(final T entity) {
        // is it a new entity?
        if (entity.getId() == null) {
            getEm().persist(entity);
        } // must be an update
        else {
            getEm().merge(entity);
        }
        return entity;
    }

    @Override
    public T findOne(final ID id) {
        return getEm().find(getClazz(), id);
    }

    @Override
    public void delete(final T entity) {
        getEm().remove(entity);
    }

    @Override
    public void delete(final ID id) {
        final T entity = em.find(getClazz(), id);
        em.remove(entity);
    }

    @Override
    public int batchSave(final List<T> records)
    {
        throw new NotImplementedException("method not yet implemented");
    }
}
