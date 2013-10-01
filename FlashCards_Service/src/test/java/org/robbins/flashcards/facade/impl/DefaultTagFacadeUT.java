package org.robbins.flashcards.facade.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.facade.TagFacade;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.TagService;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;
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
}
