package org.robbins.flashcards.repository.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.jpa.base.AbstractCrudRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

@Repository
public class FlashCardRepositoryImpl extends
		AbstractCrudRepositoryImpl<FlashCard> implements FlashCardRepository {
	static Logger logger = Logger.getLogger(FlashCardRepositoryImpl.class);

	@Override
	public Class<FlashCard> getClazz() {
		return FlashCard.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FlashCard> findAll() {
		return getEm().createQuery("from FlashCard").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FlashCard> findAll(Sort sort) {
		String sortOder = null;
		String sortDirection = null;

		for (Order order : sort) {
			sortOder = order.getProperty();
			sortDirection = order.getDirection().toString();
		}
		return getEm().createQuery(
				"from FlashCard order by " + sortOder + " " + sortDirection)
				.getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<FlashCard> findAll(Pageable page) {
		Query query = getEm().createQuery("from FlashCard");
		query.setFirstResult((page.getPageNumber() + 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());

		return new PageImpl(query.getResultList());
	}

	@Override
	public List<FlashCard> findByTagsIn(Set<Tag> tags) {
		return findByTags(tags, null);
	}

	@Override
	public List<FlashCard> findByTagsIn(Set<Tag> tags, Pageable page) {
		return findByTags(tags, page);
	}

	@SuppressWarnings("unchecked")
	private List<FlashCard> findByTags(Set<Tag> tags, Pageable page) {
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
		query.setParameter("tag_count", new Long(tagIds.size()));
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FlashCard> findByQuestionLike(String question) {
		Query query = getEm().createQuery("from Flashcard where question like :question");
		query.setParameter("question", question);
		return query.getResultList();
	}

	@Override
	public List<FlashCard> findByQuestionLike(String question, Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FlashCard findByQuestion(String question) {
		Query query = getEm().createQuery(
				"from FlashCard where question = :question");
		query.setParameter("question", question);
		List<FlashCard> results = query.getResultList();
		if (!results.isEmpty()) {
			return results.get(0);
		} else
			return null;
	}

	@Override
	public Long count() {
		Query query = getEm().createQuery("SELECT COUNT(*) FROM FlashCard");
		return (Long) query.getSingleResult();
	}
}
