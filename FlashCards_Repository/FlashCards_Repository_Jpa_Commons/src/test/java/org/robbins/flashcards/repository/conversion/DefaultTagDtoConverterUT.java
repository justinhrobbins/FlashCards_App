package org.robbins.flashcards.repository.conversion;

import java.util.Arrays;
import java.util.List;

import org.dozer.Mapper;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.conversion.impl.DefaultTagDtoConverter;
import org.robbins.tests.BaseMockingTest;
import org.springframework.test.util.ReflectionTestUtils;

public class DefaultTagDtoConverterUT extends BaseMockingTest {

    private DtoConverter converter;
    private Tag tag;
    private TagDto tagDto;
    private final String TAG_NAME = "tag name";

    @Mock
    private Mapper mockMapper;

    @Before
    public void setup()
    {
        converter = new DefaultTagDtoConverter();
        ReflectionTestUtils.setField(converter, "mapper", mockMapper);

        tag = new Tag();
        tag.setName(TAG_NAME);

        tagDto = new TagDto();
        tagDto.setName(TAG_NAME);
    }

    @Test
    public void getDtos() throws RepositoryException
	{
        List<Tag> mockTagList = Arrays.asList(tag);
        Mockito.when(mockMapper.map(tag, TagDto.class)).thenReturn(tagDto);

        List<TagDto> results = converter.getDtos(mockTagList, null);

        Assert.assertThat(results, CoreMatchers.is(List.class));
        Mockito.verify(mockMapper).map(tag, TagDto.class);
    }

    @Test
    public void getEntities() throws ServiceException {
        List<TagDto> mockTagDtoList = Arrays.asList(tagDto);
        Mockito.when(mockMapper.map(tagDto, Tag.class)).thenReturn(tag);

        List<Tag> results = converter.getEtnties(mockTagDtoList);

        Assert.assertThat(results, CoreMatchers.is(List.class));
        Mockito.verify(mockMapper).map(tagDto, Tag.class);
    }
}
