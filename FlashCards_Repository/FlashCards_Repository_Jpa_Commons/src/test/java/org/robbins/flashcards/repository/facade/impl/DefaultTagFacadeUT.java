
package org.robbins.flashcards.repository.facade.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.robbins.flashcards.repository.auditing.AuditingAwareUser;
import org.robbins.flashcards.repository.util.FieldInitializerUtil;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class DefaultTagFacadeUT extends BaseMockingTest {

    @Mock
    private TagRepository repository;

    @Mock
    private DtoConverter<TagDto, Tag> converter;

    @Mock
    private Tag mockTag;

    @Mock
    private TagDto mockTagDto;

    @Mock
    private AuditingAwareUser auditorAware;

    @Mock
    private FieldInitializerUtil fieldInitializer;

    private TagFacade tagFacade;

    private final Integer PAGE_SIZE = 10;

    @Before
    public void before() {
        tagFacade = new DefaultTagRepositoryFacade();
        ReflectionTestUtils.setField(tagFacade, "auditorAware", auditorAware);
        ReflectionTestUtils.setField(tagFacade, "repository", repository);
        ReflectionTestUtils.setField(tagFacade, "converter", converter);
        ReflectionTestUtils.setField(tagFacade, "fieldInitializer", fieldInitializer);
    }

    @Test
    public void findByName() throws FlashcardsException
	{
        when(repository.findByName(any(String.class))).thenReturn(mockTag);
        when(converter.getDto(mockTag, null)).thenReturn(mockTagDto);

        TagDto result = tagFacade.findByName(any(String.class));

        verify(repository).findByName(any(String.class));
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void findByName_ReturnNull() throws FlashcardsException {
        when(repository.findByName(any(String.class))).thenReturn(null);

        TagDto result = tagFacade.findByName(any(String.class));

        verify(repository).findByName(any(String.class));
        assertThat(result, is(nullValue()));
    }

    @Test
    public void count() {
        when(repository.count()).thenReturn(1L);

        Long result = tagFacade.count();

        verify(repository).count();
        assertThat(result, is(1L));
    }

    @Test
    public void findOne() throws FlashcardsException {
        when(repository.findOne(any(String.class))).thenReturn(mockTag);
        when(converter.getDto(mockTag, null)).thenReturn(mockTagDto);

        TagDto result = tagFacade.findOne(any(Long.class));

        verify(repository).findOne(any(String.class));
        verify(converter).getDto(mockTag, null);
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void findOne_WithFields() throws FlashcardsException {
        TagDto tagDto = new TagDto();
        Set<String> fields = new HashSet<String>(Arrays.asList("flashcards"));

        when(repository.findOne(any(String.class))).thenReturn(mockTag);
        when(converter.getDto(mockTag, fields)).thenReturn(tagDto);

        TagDto result = tagFacade.findOne(any(Long.class), fields);

        verify(repository).findOne(any(String.class));
        verify(converter).getDto(mockTag, fields);
        assertThat(result, is(TagDto.class));
    }

    @Test
    public void findOne_ReturnsNull() throws FlashcardsException {
        when(repository.findOne(any(String.class))).thenReturn(null);

        TagDto result = tagFacade.findOne(any(Long.class));

        verify(repository).findOne(any(String.class));
        assertThat(result, is(nullValue()));
    }

    @Test
    public void delete() throws ServiceException {
        tagFacade.delete(any(Long.class));

        verify(repository).delete(any(String.class));
    }

    @Test
    public void save() throws FlashcardsException {
        when(repository.save(any(Tag.class))).thenReturn(mockTag);
        when(converter.getDto(mockTag, null)).thenReturn(mockTagDto);
        when(converter.getEntity(mockTagDto)).thenReturn(mockTag);

        TagDto result = tagFacade.save(mockTagDto);

        verify(repository).save(any(Tag.class));
        assertThat(result, is(TagDto.class));
    }
}
