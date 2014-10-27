
package org.robbins.flashcards.service.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.TagServiceImpl;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class AbstractCrudServiceImplUT extends BaseMockingTest {

    @Mock
    private TagFacade facade;

    @Mock
    private TagDto tagDto;

    @Mock
    private List<TagDto> tags;

    private TagServiceImpl tagService;

    @Before
    public void before() {
        tagService = new TagServiceImpl();
        ReflectionTestUtils.setField(tagService, "facade", facade);
    }

    @Test
    public void save() throws FlashcardsException
	{
        when(facade.save(tagDto)).thenReturn(tagDto);

        final TagDto result = tagService.save(tagDto);

        verify(facade, Mockito.times(1)).save(tagDto);
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void count() {
		final Long count = 1L;
        when(facade.count()).thenReturn(count);

		final Long results = tagService.count();

        verify(facade, Mockito.times(1)).count();
        assertThat(results, is(Long.class));
    }

    @Test
    public void findOne() throws FlashcardsException {
        when(facade.findOne(Matchers.anyLong())).thenReturn(tagDto);

		final TagDto result = tagService.findOne(1L);

        verify(facade, Mockito.times(1)).findOne(1L);
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void delete() {
		final Long id = 1L;

        tagService.delete(id);

        verify(facade, Mockito.times(1)).delete(id);
    }

    @Test
    public void deleteEntity() {

        tagService.delete(tagDto.getId());

        verify(facade, Mockito.times(1)).delete(tagDto.getId());
    }

    @Test
    public void findAll() throws FlashcardsException {
        when(facade.list()).thenReturn(tags);

		final List<TagDto> results = tagService.findAll();

        verify(facade, Mockito.times(1)).list();
        assertThat(results, is(List.class));
    }

    @Test
    public void findAllSort() throws FlashcardsException {
		final String SORT = "PROPERTY_TO_SORT_BY";
        when(facade.list(null, null, SORT, null)).thenReturn(tags);

        final List<TagDto> results = tagService.findAll(null, null, SORT, null);

        verify(facade, Mockito.times(1)).list(null, null, SORT, null);
        assertThat(results, is(List.class));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void findAllPageable() throws FlashcardsException {
		final Integer PAGE = 1;
		final Integer PAGE_SIZE = 1;
        when(facade.list(PAGE, PAGE_SIZE, null, null)).thenReturn(tags);

        final List<TagDto> results = tagService.findAll(PAGE, PAGE_SIZE, null, null);

        verify(facade, Mockito.times(1)).list(PAGE, PAGE_SIZE, null, null);
        assertThat(results, is(List.class));
    }
}
