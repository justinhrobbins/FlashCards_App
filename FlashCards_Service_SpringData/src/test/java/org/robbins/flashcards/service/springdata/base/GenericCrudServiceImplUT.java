
package org.robbins.flashcards.service.springdata.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.springdata.TagRepository;
import org.robbins.flashcards.service.springdata.TagServiceImpl;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class GenericCrudServiceImplUT extends BaseMockingTest {

    @Mock
    private TagRepository repository;

    private TagServiceImpl tagService;

    @Mock
    private Tag tag;

    @Mock
    private List<Tag> tags;

    @Before
    public void before() {
        tagService = new TagServiceImpl();
        ReflectionTestUtils.setField(tagService, "repository", repository);
    }

    @Test
    public void save() {
        when(repository.save(tag)).thenReturn(tag);

        Tag result = tagService.save(tag);

        verify(repository, Mockito.times(1)).save(tag);
        assertThat(result, is(Tag.class));
    }

    @Test
    public void findOne() {
        when(repository.findOne(Matchers.anyLong())).thenReturn(tag);

        Tag result = tagService.findOne(1L);

        verify(repository, Mockito.times(1)).findOne(1L);
        assertThat(result, is(Tag.class));
    }

    @Test
    public void exists() {
        when(repository.exists(Matchers.anyLong())).thenReturn(Boolean.TRUE);

        Boolean result = tagService.exists(1L);

        verify(repository, Mockito.times(1)).exists(1L);
        assertThat(result, is(Boolean.class));
        assertTrue(result.booleanValue());
    }

    @Test
    public void findAllIterable() {
        @SuppressWarnings("unchecked")
        List<Long> ids = mock(List.class);
        when(repository.findAll(ids)).thenReturn(tags);

        Iterable<Tag> results = tagService.findAll(ids);

        verify(repository, Mockito.times(1)).findAll(ids);
        assertThat(results, is(List.class));
    }

    @Test
    public void count() {
        Long count = 1L;
        when(repository.count()).thenReturn(count);

        Long results = tagService.count();

        verify(repository, Mockito.times(1)).count();
        assertThat(results, is(Long.class));
    }

    @Test
    public void delete() {
        Long id = 1L;

        tagService.delete(id);

        verify(repository, Mockito.times(1)).delete(id);
    }

    @Test
    public void deleteEntity() {

        tagService.delete(tag);

        verify(repository, Mockito.times(1)).delete(tag);
    }

    @Test
    public void deleteIterable() {
        tagService.delete(tags);

        verify(repository, Mockito.times(1)).delete(tags);
    }

    @Test
    public void deleteAll() {
        tagService.deleteAll();

        verify(repository, Mockito.times(1)).deleteAll();
    }
}
