
package org.robbins.flashcards.jpa.repository;

import org.joda.time.DateTime;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.model.common.AbstractAuditable;
import org.robbins.flashcards.repository.FlashCardsAppRepository;
import org.robbins.flashcards.repository.auditing.AuditingAwareUser;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractCrudRepositoryImpl<T extends AbstractAuditable<User, Long>>
        implements FlashCardsAppRepository<T, Long> {

    @PersistenceContext
    private EntityManager em;

    public abstract Class<T> getClazz();

    @Inject
    private AuditingAwareUser auditorAware;

    public User getAuditingUser() {
        return getEm().find(User.class, auditorAware.getCurrentAuditor().getId());
    }

    public EntityManager getEm() {
        return em;
    }

    @Override
    public T save(final T entity) {
        entity.setLastModifiedBy(getAuditingUser());
        entity.setLastModifiedDate(new DateTime());

        // is it a new entity?
        if ((entity.getId() == null) || (entity.getId() == 0)) {
            entity.setCreatedBy(getAuditingUser());
            entity.setCreatedDate(new DateTime());
            getEm().persist(entity);
        } // must be an update
        else {
            getEm().merge(entity);
        }
        return entity;
    }

    @Override
    public T findOne(final Long id) {
        return getEm().find(getClazz(), id);
    }

    @Override
    public void delete(final T entity) {
        getEm().remove(entity);
    }

    @Override
    public void delete(final Long id) {
        T entity = em.find(getClazz(), id);
        em.remove(entity);
    }
}
