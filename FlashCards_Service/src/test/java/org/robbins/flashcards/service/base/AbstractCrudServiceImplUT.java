
package org.robbins.flashcards.service.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.service.TagServiceImpl;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class AbstractCrudServiceImplUT extends BaseMockingTest {

    @Mock
    private TagRepository repository;

    @Mock
    private Tag tag;

    @Mock
    private List<Tag> tags;

    private TagServiceImpl tagService;

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
    public void count() {
        Long count = 1L;
        when(repository.count()).thenReturn(count);

        Long results = tagService.count();

        verify(repository, Mockito.times(1)).count();
        assertThat(results, is(Long.class));
    }

    @Test
    public void findOne() {
        when(repository.findOne(Matchers.anyLong())).thenReturn(tag);

        Tag result = tagService.findOne(1L);

        verify(repository, Mockito.times(1)).findOne(1L);
        assertThat(result, is(Tag.class));
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
    public void findAll() {
        when(repository.findAll()).thenReturn(tags);

        List<Tag> results = tagService.findAll();

        verify(repository, Mockito.times(1)).findAll();
        assertThat(results, is(List.class));
    }

    @Test
    public void findAllSort() {
        Sort sort = mock(Sort.class);
        when(repository.findAll(sort)).thenReturn(tags);

        List<Tag> results = tagService.findAll(sort);

        verify(repository, Mockito.times(1)).findAll(sort);
        assertThat(results, is(List.class));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void findAllPageable() {
        Pageable pageable = mock(Pageable.class);
        Page page = mock(Page.class);
        when(repository.findAll(pageable)).thenReturn(page);

        List<Tag> results = tagService.findAll(pageable);

        verify(repository, Mockito.times(1)).findAll(pageable);
        assertThat(results, is(List.class));
    }
}
