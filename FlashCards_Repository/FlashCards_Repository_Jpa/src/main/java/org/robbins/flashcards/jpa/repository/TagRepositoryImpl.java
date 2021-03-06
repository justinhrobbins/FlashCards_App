
package org.robbins.flashcards.jpa.repository;

import org.robbins.flashcards.jpa.repository.util.JpqlUtil;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.batch.BatchSavingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TagRepositoryImpl extends AbstractCrudRepositoryImpl<Tag, Long> implements
        TagRepository<Tag, Long>, BatchSavingRepository<Tag>
{
    @Override
    public Class<Tag> getClazz() {
        return Tag.class;
    }

    @Inject
    private BatchSavingRepository<Tag> batchTagRepository;

    @SuppressWarnings("unchecked")
    @Override
    public List<Tag> findAll() {
        return getEm().createQuery("from Tag").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tag> findAll(final Sort sort) {
        return getEm().createQuery("from Tag order by " + JpqlUtil.sortAsString(sort)).getResultList();
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
    public List<Tag> findByFlashCards_Id(final Long flashCardId) {
        Query query = getEm().createQuery("SELECT t FROM Tag t JOIN t.flashCards f WHERE f.id = :flashcardId");
        query.setParameter("flashcardId", flashCardId);
        return query.getResultList();
    }

    @Override
    public List<Tag> findByCreatedBy_Id(final Long userId) {
        Query query = getEm().createQuery("SELECT t FROM Tag t JOIN t.createdBy u WHERE u.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public int batchSave(final List<Tag> records)
    {
        return batchTagRepository.batchSave(records);
    }
}
