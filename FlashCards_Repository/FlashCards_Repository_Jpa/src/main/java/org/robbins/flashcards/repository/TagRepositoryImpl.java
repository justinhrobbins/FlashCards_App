
package org.robbins.flashcards.repository;

import org.robbins.flashcards.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class TagRepositoryImpl extends AbstractCrudRepositoryImpl<Tag> implements
        TagRepository {

    @Override
    public Class<Tag> getClazz() {
        return Tag.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tag> findAll() {
        return getEm().createQuery("from Tag").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tag> findAll(final Sort sort) {
        String sortOder = null;
        String sortDirection = null;

        for (Order order : sort) {
            sortOder = order.getProperty();
            sortDirection = order.getDirection().toString();
        }
        return getEm().createQuery("from Tag order by " + sortOder + " " + sortDirection).getResultList();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Page<Tag> findAll(final Pageable page) {
        Query query = getEm().createQuery("from Tag");
        query.setFirstResult((page.getPageNumber() + 1) * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        return new PageImpl(query.getResultList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Tag findByName(final String name) {
        Query query = getEm().createQuery("from Tag where name = :name");
        query.setParameter("name", name);
        List<Tag> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public long count() {
        Query query = getEm().createQuery("SELECT COUNT(*) FROM Tag");
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Tag> findByFlashcards_Id(Long flashcardId) {
        Query query = getEm().createQuery("SELECT t FROM Tag t JOIN t.flashcards f WHERE f.id = :flashcardId");
        query.setParameter("flashcardId", flashcardId);
        return query.getResultList();
    }
}
