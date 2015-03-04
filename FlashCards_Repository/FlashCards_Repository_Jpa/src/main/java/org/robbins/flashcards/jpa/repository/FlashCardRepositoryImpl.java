
package org.robbins.flashcards.jpa.repository;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardRepository;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class FlashCardRepositoryImpl extends AbstractCrudRepositoryImpl<FlashCard, String>
        implements FlashCardRepository<FlashCard, Tag, String> {

    @Override
    public Class<FlashCard> getClazz() {
        return FlashCard.class;
    }

    @Override
    public FlashCard save(final FlashCard flashCard) {
        for (Tag tag : flashCard.getTags()) {
            if ((tag.getId() == null) || (StringUtils.isEmpty(tag.getId()))) {
                tag.setCreatedBy(getAuditingUser());
                tag.setCreatedDate(new DateTime());
                tag.setLastModifiedBy(getAuditingUser());
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
    public List<FlashCard> findByTagsIn(final Set<Tag> tags, final PageRequest page) {
        return findByTags(tags, page);
    }

    @SuppressWarnings("unchecked")
    private List<FlashCard> findByTags(final Set<Tag> tags, final Pageable page) {
        List<String> tagIds = new ArrayList<>();
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
    public List<FlashCard> findByQuestionLike(final String question, final PageRequest page) {
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

    @Override
    public List<FlashCard> findByTags_Id(String tagId) {
        Query query = getEm().createQuery("SELECT f FROM FlashCard f JOIN f.tags t WHERE t.id = :tagId");
        query.setParameter("tagId", tagId);
        return query.getResultList();
    }

    @Override
    public List<FlashCard> findByCreatedBy_Id(String userId) {
        Query query = getEm().createQuery("SELECT f FROM FlashCard f JOIN f.createdBy u WHERE u.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

}
