
package org.robbins.flashcards.repository.jpa.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.jpa.repository.TagRepositoryImpl;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.auditing.AuditingAwareUser;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@Category(UnitTest.class)
public class AbstractCrudRepositoryImplUT extends BaseMockingTest {

    @Mock
    private EntityManager em;

    @Mock
    private Tag tag;

    @Mock
    private AuditingAwareUser auditorAware;

    @Mock
    private User mockUser;

    @Mock
    private UserDto mockAuditor;

    private Query query;

    private List<Tag> results;

    private TagRepository repository;

    @Before
    public void before() {
        repository = new TagRepositoryImpl();
        query = mock(Query.class);
        results = new ArrayList<>();
        results.add(new Tag());
        ReflectionTestUtils.setField(repository, "em", em);
        ReflectionTestUtils.setField(repository, "auditorAware", auditorAware);

        when(em.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(results);
        when(auditorAware.getCurrentAuditor()).thenReturn(mockUser);
        when(mockAuditor.getId()).thenReturn(1L);
        when(em.find(User.class, 1L)).thenReturn(mockUser);
    }

    @Test
    public void save() {
        Tag result = repository.save(tag);

        verify(em, times(1)).persist(tag);
        assertThat(result, is(Tag.class));
    }

    @Test
    public void update() {
        Tag tag = new Tag(1L);
        Tag result = repository.save(tag);

        verify(em, times(1)).merge(tag);
        assertThat(result, is(Tag.class));
    }

    @Test
    public void findOne() {
        Long id = 1L;
        Tag result = mock(Tag.class);
        when(em.find(Tag.class, id)).thenReturn(result);

        result = repository.findOne(id);

        verify(em, times(1)).find(Tag.class, id);
        assertThat(result, is(Tag.class));
    }

    @Test
    public void deleteByEntity() {
        repository.delete(tag);

        verify(em, times(1)).remove(tag);
    }

    @Test
    public void deleteById() {
        Long id = 1L;
        Tag result = mock(Tag.class);
        when(em.find(Tag.class, id)).thenReturn(result);

        repository.delete(id);

        verify(em, times(1)).remove(result);
    }
}
