
package org.robbins.flashcards.jpa.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.jpa.repository.UserRepositoryImpl;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;

import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@Category(UnitTest.class)
public class UserRepositoryImplUT extends BaseMockingTest {

    @Mock
    private EntityManager em;

    private Query query;

    private List<User> results;

    private UserRepository<User, Long> repository;

    @Before
    public void before() {
        repository = new UserRepositoryImpl();
        query = Mockito.mock(Query.class);
        results = new ArrayList<User>();
        results.add(new User());
        ReflectionTestUtils.setField(repository, "em", em);

        when(em.createQuery(Matchers.anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(results);
    }

    @Test
    public void testFindUserByOpenid() {
        when(query.getSingleResult()).thenReturn(new User());

        User user = repository.findUserByOpenid("open_id");

        Mockito.verify(query, Mockito.times(1)).getSingleResult();
        assertThat(user, is(User.class));
    }

    @Test
    public void testFindAll() {
        List<User> users = repository.findAll();

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(users, is(List.class));
    }

    @Test
    public void testfindAllPageable() {
        Pageable pageRequest = new PageRequest(1, 1);
        Page<User> results = repository.findAll(pageRequest);

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(results, is(Page.class));
    }

    @Test
    public void testfindAllSort() {
        Sort sort = new Sort("name");
        List<User> results = repository.findAll(sort);

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(results, is(List.class));
    }

    @Test
    public void testCount() {
        when(query.getSingleResult()).thenReturn(2L);

        Long count = repository.count();

        Mockito.verify(query, Mockito.times(1)).getSingleResult();
        assertThat(count, is(Long.class));
    }
}
