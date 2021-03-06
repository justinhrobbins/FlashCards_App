
package org.robbins.flashcards.jpa.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.jpa.repository.FlashCardRepositoryImpl;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.FlashCardRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@Category(UnitTest.class)
public class FlashCardRepositoryImplUT extends BaseMockingTest {

    @Mock
    private EntityManager em;

    private Query query;

    private List<FlashCard> results;

    private FlashCardRepository<FlashCard, Tag, Long> repository;

    @Before
    public void before() {
        repository = new FlashCardRepositoryImpl();
        query = Mockito.mock(Query.class);
        results = new ArrayList<>();
        results.add(new FlashCard());
        ReflectionTestUtils.setField(repository, "em", em);

        when(em.createQuery(Matchers.anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(results);
    }

    @Test
    public void testFindByQuestionLike() {
        List<FlashCard> results = repository.findByQuestionLike("question");

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(results, is(List.class));
    }

    @Test
    public void testFindByTagsIn() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag();
        tag.setName("tag name");
        tags.add(tag);
        List<FlashCard> results = repository.findByTagsIn(tags);

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(results, is(List.class));
    }

    @Test
    public void testFindByTagsInPageable() {
        PageRequest pageRequest = new PageRequest(1, 1);
        List<FlashCard> results = repository.findByTagsIn(new HashSet<>(), pageRequest);

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(results, is(List.class));
    }

    @Test
    public void testFindByQuestion() {
        FlashCard result = repository.findByQuestion("question");

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(result, is(FlashCard.class));
    }

    @Test
    public void testFindByQuestion_NoResults() {
        List<FlashCard> results = new ArrayList<FlashCard>();
        when(query.getResultList()).thenReturn(results);

        FlashCard flashCard = repository.findByQuestion("question");

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(flashCard, is(nullValue()));
    }

    @Test
    public void testFindAll() {
        List<FlashCard> results = repository.findAll();

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(results, is(List.class));
    }

    @Test
    public void testfindAllPageable() {
        Pageable pageRequest = new PageRequest(1, 1);
        Page<FlashCard> results = repository.findAll(pageRequest);

        Mockito.verify(query, Mockito.times(1)).getResultList();
        assertThat(results, is(Page.class));
    }

    @Test
    public void testfindAllSort() {
        Sort sort = new Sort("name");
        List<FlashCard> results = repository.findAll(sort);

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
