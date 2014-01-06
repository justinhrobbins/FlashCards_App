package org.robbins.flashcards.facade.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.TagService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;


@Category(UnitTest.class)
public class DefaultTagFacadeUT extends BaseMockingTest {

	@Mock
	TagService mockService;
	
	@Mock
	Mapper mockMapper;
	
	@Mock
	Tag mockTag;
	
	@Mock
	TagDto mockTagDto;
	
	TagFacade tagFacade;
	
	@Before
	public void before() {
		tagFacade = new DefaultTagFacade();
		ReflectionTestUtils.setField(tagFacade, "service", mockService);
		ReflectionTestUtils.setField(tagFacade, "mapper", mockMapper);
	}
	
	@Test
	public void findByName() {
		when(mockService.findByName(any(String.class))).thenReturn(mockTag);
		when(mockMapper.map(mockTag, TagDto.class)).thenReturn(mockTagDto);
		
		TagDto result = tagFacade.findByName(any(String.class));
		
		verify(mockService).findByName(any(String.class));
		assertThat(result, is(TagDto.class));
	}
	
	@Test
	public void findOne() throws ServiceException {
		when(mockService.findOne(any(Long.class))).thenReturn(mockTag);
		when(mockMapper.map(mockTag, TagDto.class)).thenReturn(mockTagDto);
		
		TagDto result = tagFacade.findOne(any(Long.class));
		
		verify(mockService).findOne(any(Long.class));
		assertThat(result, is(TagDto.class));
	}
	
	@Test
	public void delete() throws ServiceException {
		tagFacade.delete(any(Long.class));
		
		verify(mockService).delete(any(Long.class));
	}
	
	@Test
	public void save() throws ServiceException {
		when(mockService.save(any(Tag.class))).thenReturn(mockTag);
		when(mockMapper.map(mockTag, TagDto.class)).thenReturn(mockTagDto);
		when(mockMapper.map(mockTagDto, Tag.class)).thenReturn(mockTag);
		
		TagDto result = tagFacade.save(mockTagDto);
		
		verify(mockService).save(any(Tag.class));
		assertThat(result, is(TagDto.class));		
	}
	
	@Test
	public void list() throws ServiceException {
		List<Tag> mockTagList = Arrays.asList(mockTag);

		when(mockMapper.map(mockTag, TagDto.class)).thenReturn(mockTagDto);
		when(mockService.findAll()).thenReturn(mockTagList);
		
		List<TagDto> results = tagFacade.list(null, null, null, null);

		verify(mockService).findAll();
		assertThat(results, is(List.class));		
	}

	@Test
	public void listReturnNull() throws ServiceException {
		when(mockService.findAll()).thenReturn(null);
		
		List<TagDto> results = tagFacade.list(null, null, null, null);

		verify(mockService).findAll();
		assertThat(results, is(nullValue()));		
	}
	
	@Test
	public void listWithPage() throws ServiceException {
		List<Tag> mockTagList = Arrays.asList(mockTag);

		when(mockMapper.map(mockTag, TagDto.class)).thenReturn(mockTagDto);
		when(mockService.findAll(any(PageRequest.class))).thenReturn(mockTagList);
		
		List<TagDto> results = tagFacade.list(1, 10, "name", "asc");

		verify(mockService).findAll(any(PageRequest.class));
		assertThat(results, is(List.class));		
	}
}
