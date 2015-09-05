
package org.robbins.flashcards.jpa.repository;

import org.robbins.flashcards.jpa.repository.util.JpqlUtil;
import org.robbins.flashcards.model.BulkLoadingReceipt;
import org.robbins.flashcards.repository.BulkLoadingReceiptRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class BulkLoadingReceiptRepositoryImpl extends AbstractCrudRepositoryImpl<BulkLoadingReceipt, String> implements
        BulkLoadingReceiptRepository<BulkLoadingReceipt, String> {

    @Override
    public Class<BulkLoadingReceipt> getClazz() {
        return BulkLoadingReceipt.class;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Page<BulkLoadingReceipt> findAll(final Pageable page) {
        Query query = getEm().createQuery("from Tag");
        query.setFirstResult((page.getPageNumber() + 1) * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        return new PageImpl(query.getResultList());
    }

    @Override
    public List<BulkLoadingReceipt> findByCreatedBy_Id(final String userId) {
        Query query = getEm().createQuery("SELECT t FROM BulkLoadingReceipt r JOIN r.createdBy u WHERE u.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public long count() {
        Query query = getEm().createQuery("SELECT COUNT(*) FROM BulkLoadingReceipt");
        return (Long) query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BulkLoadingReceipt> findAll(final Sort sort) {
        return getEm().createQuery("from BulkLoadingReceipt order by " + JpqlUtil.sortAsString(sort)).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BulkLoadingReceipt> findAll() {
        return getEm().createQuery("from BulkLoadingReceipt").getResultList();
    }
}
