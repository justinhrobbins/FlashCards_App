
package org.robbins.flashcards.jpa.repository;

import org.robbins.flashcards.jpa.repository.util.JpqlUtil;
import org.robbins.flashcards.model.BatchLoadingReceipt;
import org.robbins.flashcards.repository.BatchLoadingReceiptRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class BatchLoadingReceiptRepositoryImpl extends AbstractCrudRepositoryImpl<BatchLoadingReceipt, Long> implements
        BatchLoadingReceiptRepository<BatchLoadingReceipt, Long> {

    @Override
    public Class<BatchLoadingReceipt> getClazz() {
        return BatchLoadingReceipt.class;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Page<BatchLoadingReceipt> findAll(final Pageable page) {
        Query query = getEm().createQuery("from Tag");
        query.setFirstResult((page.getPageNumber() + 1) * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        return new PageImpl(query.getResultList());
    }

    @Override
    public List<BatchLoadingReceipt> findByCreatedBy_Id(final Long userId) {
        Query query = getEm().createQuery("SELECT t FROM BatchLoadingReceipt r JOIN r.createdBy u WHERE u.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public long count() {
        Query query = getEm().createQuery("SELECT COUNT(*) FROM BatchLoadingReceipt");
        return (Long) query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BatchLoadingReceipt> findAll(final Sort sort) {
        return getEm().createQuery("from BatchLoadingReceipt order by " + JpqlUtil.sortAsString(sort)).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BatchLoadingReceipt> findAll() {
        return getEm().createQuery("from BatchLoadingReceipt").getResultList();
    }
}
