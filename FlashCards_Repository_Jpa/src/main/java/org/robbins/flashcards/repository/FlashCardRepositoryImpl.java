
package org.robbins.flashcards.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.joda.time.DateTime;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

@Repository
public class FlashCardRepositoryImpl extends AbstractCrudRepositoryImpl<FlashCard>
        implements FlashCardRepository {

    @Override
    public Class<FlashCard> getClazz() {
        return FlashCard.class;
    }

    @Override
    public FlashCard save(final FlashCard flashCard) {
        for (Tag tag : flashCard.getTags()) {
            if ((tag.getId() == null) || (tag.getId() == 0)) {
                tag.setCreatedBy(getAuditorAware().getCurrentAuditor());
                tag.setCreatedDate(new DateTime());
                tag.setLastModifiedBy(getAuditorAware().getCurrentAuditor());
                tag.setLastModifiedDate(new DateTime());
            }
        }
        return super.save(flashCard);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FlashCard> findAll() {
        return getEm().createQuery("from FlashCard").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FlashCard> findAll(final Sort sort) {
        String sortOder = null;
        String sortDirection = null;

        for (Order order : sort) {
            sortOder = order.getProperty();
            sortDirection = order.getDirection().toString();
        }
        return getEm().createQuery(
                "from FlashCard order by " + sortOder + " " + sortDirection).getResultList();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Page<FlashCard> findAll(final Pageable page) {
        Query query = getEm().createQuery("from FlashCard");
        query.setFirstResult((page.getPageNumber() + 1) * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        return new PageImpl(query.getResultList());
    }

    @Override
    public List<FlashCard> findByTagsIn(final Set<Tag> tags) {
        return findByTags(tags, null);
    }

    @Override
    public List<FlashCard> findByTagsIn(final Set<Tag> tags, final Pageable page) {
        return findByTags(tags, page);
    }

    @SuppressWarnings("unchecked")
    private List<FlashCard> findByTags(final Set<Tag> tags, final Pageable page) {
        List<Long> tagIds = new ArrayList<Long>();
        for (Tag tag : tags) {
            tagIds.add(tag.getId());
        }

        String jpql = "select fc from FlashCard fc " + "join fc.tags t "
                + "where t.id in (:tagIds) " + "group by fc "
                + "having count(t)=:tag_count " + "order by fc.question";

        Query query = getEm().createQuery(jpql);

        if (page != null) {
            query.setFirstResult((page.getPageNumber() + 1) * page.getPageSize());
            query.setMaxResults(page.getPageSize());
        }
        query.setParameter("tagIds", tagIds);
        query.setParameter("tag_count", Long.valueOf(tagIds.size()));
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FlashCard> findByQuestionLike(final String question) {
        Query query = getEm().createQuery("from Flashcard where question like :question");
        query.setParameter("question", question);
        return query.getResultList();
    }

    @Override
    public List<FlashCard> findByQuestionLike(final String question, final Pageable page) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public FlashCard findByQuestion(final String question) {
        Query query = getEm().createQuery("from FlashCard where question = :question");
        query.setParameter("question", question);
        List<FlashCard> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public long count() {
        Query query = getEm().createQuery("SELECT COUNT(*) FROM FlashCard");
        return (Long) query.getSingleResult();
    }
}
