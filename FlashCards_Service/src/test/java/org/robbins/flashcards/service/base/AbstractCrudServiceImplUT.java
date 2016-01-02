
package org.robbins.flashcards.service.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;
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

    private final Long uuid = RandomUtils.nextLong(0L, Long.MAX_VALUE);

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

		final TagDto result = tagService.findOne(uuid);

        verify(facade, Mockito.times(1)).findOne(uuid);
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void delete() {
        tagService.delete(uuid);

        verify(facade, Mockito.times(1)).delete(uuid);
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
}
