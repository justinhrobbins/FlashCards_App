
package org.robbins.flashcards.repository.jpa.base;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.robbins.flashcards.auditing.AuditingAwareUser;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.model.common.AbstractAuditable;

public abstract class AbstractCrudRepositoryImpl<T extends AbstractAuditable<User, Long>>
        implements CrudRepository<T> {

    @PersistenceContext
    private EntityManager em;

    public abstract Class<T> getClazz();

    @Inject
    private AuditingAwareUser auditorAware;

    public AuditingAwareUser getAuditorAware() {
        return auditorAware;
    }

    public EntityManager getEm() {
        return em;
    }

    @Override
    public T save(T entity) {
        entity.setLastModifiedBy(getAuditorAware().getCurrentAuditor());
        entity.setLastModifiedDate(new DateTime());

        // is it a new entity?
        if ((entity.getId() == null) || (entity.getId() == 0)) {
            entity.setCreatedBy(getAuditorAware().getCurrentAuditor());
            entity.setCreatedDate(new DateTime());
            getEm().persist(entity);
        }
        // must be an update
        else {
            getEm().merge(entity);
        }
        return entity;
    }

    @Override
    public T findOne(Long id) {
        return getEm().find(getClazz(), id);
    }

    @Override
    public void delete(T entity) {
        getEm().remove(entity);
    }

    @Override
    public void delete(Long id) {
        T entity = em.find(getClazz(), id);
        em.remove(entity);
    }
}
