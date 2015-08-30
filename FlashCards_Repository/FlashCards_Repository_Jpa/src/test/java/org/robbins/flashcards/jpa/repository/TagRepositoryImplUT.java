
package org.robbins.flashcards.jpa.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.jpa.repository.TagRepositoryImpl;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
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
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@Category(UnitTest.class)
public class TagRepositoryImplUT extends BaseMockingTest {

    @Mock
    private EntityManager em;

    private Query query;

    private List<Tag> results;

    private TagRepository<Tag, String> repository;

    @Before
    public void before() {
        repository = new TagRepositoryImpl();
        query = Mockito.mock(Query.class);
        results = new ArrayList<Tag>();
        results.add(new Tag());
        ReflectionTestUtils.setField(repository, "em", em);

        when(em.createQuery(Matchers.anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(results);
    }

    @Test
    public void testFindByName() {
        Tag tag = repository.findByName("EJB");

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(tag, is(Tag.class));
    }

    @Test
    public void testFindByName_NoResults() {
        List<Tag> results = new ArrayList<Tag>();
        when(query.getResultList()).thenReturn(results);

        Tag tag = repository.findByName("EJB");

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(tag, is(nullValue()));
    }

    @Test
    public void testFindAll() {
        List<Tag> tags = repository.findAll();

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(tags, is(List.class));
    }

    @Test
    public void testfindAllPageable() {
        Pageable pageRequest = new PageRequest(1, 1);
        Page<Tag> results = repository.findAll(pageRequest);

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(results, is(Page.class));
    }

    @Test
    public void testfindAllSort() {
        Sort sort = new Sort("name");
        List<Tag> results = repository.findAll(sort);

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
