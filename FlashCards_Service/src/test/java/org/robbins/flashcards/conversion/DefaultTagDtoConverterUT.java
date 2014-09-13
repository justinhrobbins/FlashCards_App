package org.robbins.flashcards.conversion;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robbins.flashcards.conversion.impl.DefaultTagDtoConverter;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.robbins.flashcards.model.Tag;
import org.robbins.tests.BaseMockingTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void getDtos() throws ServiceException {
        List<Tag> mockTagList = Arrays.asList(tag);
        when(mockMapper.map(tag, TagDto.class)).thenReturn(tagDto);

        List<TagDto> results = converter.getDtos(mockTagList, null);

        assertThat(results, is(List.class));
        verify(mockMapper).map(tag, TagDto.class);
    }

    @Test
    public void getEntities() throws ServiceException {
        List<TagDto> mockTagDtoList = Arrays.asList(tagDto);
        when(mockMapper.map(tagDto, Tag.class)).thenReturn(tag);

        List<Tag> results = converter.getEtnties(mockTagDtoList);

        assertThat(results, is(List.class));
        verify(mockMapper).map(tagDto, Tag.class);
    }
}
