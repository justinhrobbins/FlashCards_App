
package org.robbins.flashcards.jpa.repository.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.jpa.repository.TagRepositoryImpl;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.auditing.AuditingAwareUser;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private UserDto mockAuditor;

    private Query query;

    private List<Tag> results;

    private TagRepository<Tag, Long> repository;

    private final Long id = 1L;
    private final Long uuid = 1L;

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
        when(auditorAware.getCurrentAuditor()).thenReturn(1L);
        when(mockAuditor.getId()).thenReturn(uuid);
        when(em.find(String.class, id)).thenReturn("");
    }

    @Test
    public void save() {
        Tag result = repository.save(tag);

        verify(em, times(1)).persist(tag);
        assertThat(result, is(Tag.class));
    }

    @Test
    public void update() {
        Tag tag = new Tag(id);
        Tag result = repository.save(tag);

        verify(em, times(1)).merge(tag);
        assertThat(result, is(Tag.class));
    }

    @Test
    public void findOne() {
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
        Tag result = mock(Tag.class);
        when(em.find(Tag.class, id)).thenReturn(result);

        repository.delete(id);

        verify(em, times(1)).remove(result);
    }
}
