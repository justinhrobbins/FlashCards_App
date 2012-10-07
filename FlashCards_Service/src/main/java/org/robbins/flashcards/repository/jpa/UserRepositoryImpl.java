package org.robbins.flashcards.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.jpa.base.AbstractCrudRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public class UserRepositoryImpl extends AbstractCrudRepositoryImpl<User> implements UserRepository {

    @PersistenceContext
    private EntityManager em;

	@Override
	public Class<User> getClazz() {
		return User.class;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		return em.createQuery("from User").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll(Sort sort) {
		String sortOder = null;
		String sortDirection = null;
		
		for (Order order : sort) {
			sortOder = order.getProperty();
			sortDirection = order.getDirection().toString();
		}
		return getEm().createQuery("from User order by " + sortOder + " "  + sortDirection).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<User> findAll(Pageable page) {
		Query query = em.createQuery("from User");
		query.setFirstResult((page.getPageNumber() + 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		
		return new PageImpl(query.getResultList());
	}

	@Override
	public User findOne(Long id) {
		return em.find(User.class, id);
	}

	@Override
	public User findUserByOpenid(String openid) {
		Query query = em.createQuery("from User where openid = :openid");
		query.setParameter("openid", openid);
		return (User)query.getSingleResult();
	}
	
	@Override
	public Long count() {
		Query query = getEm().createQuery("SELECT COUNT(*) FROM User");
		return (Long) query.getSingleResult();
	}
}
