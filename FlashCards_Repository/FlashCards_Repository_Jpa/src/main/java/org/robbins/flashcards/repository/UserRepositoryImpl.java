
package org.robbins.flashcards.repository;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public class UserRepositoryImpl extends AbstractCrudRepositoryImpl<User> implements
        UserRepository {

    @Override
    public Class<User> getClazz() {
        return User.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        return getEm().createQuery("from User").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll(final Sort sort) {
        String sortOder = null;
        String sortDirection = null;

        for (Order order : sort) {
            sortOder = order.getProperty();
            sortDirection = order.getDirection().toString();
        }
        return getEm().createQuery("from User order by " + sortOder + " " + sortDirection).getResultList();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Page<User> findAll(final Pageable page) {
        Query query = getEm().createQuery("from User");
        query.setFirstResult((page.getPageNumber() + 1) * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        return new PageImpl(query.getResultList());
    }

    @Override
    public User findUserByOpenid(final String openid) {
        Query query = getEm().createQuery("from User where openid = :openid");
        query.setParameter("openid", openid);
        try
		{
			return (User) query.getSingleResult();
		}
		catch (NoResultException e)
		{
			return null;
		}

    }

    @Override
    public long count() {
        Query query = getEm().createQuery("SELECT COUNT(*) FROM User");
        return (Long) query.getSingleResult();
    }

    @Override
    public List<User> findByCreatedBy_Id(Long userId) {
        Query query = getEm().createQuery("SELECT u FROM User u JOIN u.createdBy u WHERE u.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
