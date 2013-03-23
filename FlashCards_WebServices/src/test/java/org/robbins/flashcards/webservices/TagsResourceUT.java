package org.robbins.flashcards.webservices;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.TagService;
import org.robbins.tests.BaseTest;
import org.robbins.tests.UnitTest;
import org.springframework.test.util.ReflectionTestUtils;

@Category(UnitTest.class)
public class TagsResourceUT extends BaseTest {

	@Mock TagService service;
	TagsResource resource;
	
	@Before
	public void before() {
		resource = new TagsResource();
		ReflectionTestUtils.setField(resource, "service", service);
	}
	
	@Test
	public void post() {
		when(service.save(Mockito.any(Tag.class))).thenReturn(new Tag(1L));
		
		Tag tag = resource.post(new Tag());
		
		Mockito.verify(service).save(Mockito.any(Tag.class));
		assertThat(tag, is(Tag.class));
	}
	
	@Test
	public void findOne() {
		when(service.findOne(Mockito.anyLong())).thenReturn(new Tag(1L));
		
		Tag tag = resource.findOne(1L);
		
		Mockito.verify(service).findOne(Mockito.anyLong());
		assertThat(tag, is(Tag.class));
	}
	
	@Test
	public void list() {
		when(service.findAll()).thenReturn(new ArrayList<Tag>());
		
		List<Tag> tags = resource.list(null, null, null, null);
		
		Mockito.verify(service).findAll();
		assertThat(tags, is(List.class));
	}
	
//	@Test void delete() {
//		when(service.delete(Mockito.anyLong())).thenReturn(new Response());
//	}
}
