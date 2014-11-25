
package org.robbins.flashcards.repository.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.RepositoryException;
import org.robbins.tests.BaseMockingTest;
import org.robbins.tests.UnitTest;

@Category(UnitTest.class)
public class DtoUtilUT extends BaseMockingTest {

    @Test
    public void filterFields() throws RepositoryException
	{
        String NAME = "TEST_NAME";
        TagDto tagDto = new TagDto();
        tagDto.setName(NAME);
        Set<String> fields = new HashSet<String>(Arrays.asList("name"));

        DtoUtil.filterFields(tagDto, fields);

        assertThat(tagDto.getName(), is(NAME));
    }

    @Test
    public void filterFields_WithFields() throws RepositoryException {
        String NAME = "TEST_NAME";
        TagDto tagDto = new TagDto(1L);
        tagDto.setName(NAME);
        Set<String> fields = new HashSet<String>(Arrays.asList("id"));

        DtoUtil.filterFields(tagDto, fields);

        assertThat(tagDto.getName(), is(nullValue()));
    }

    @Test
    public void filterFields_WithNullFields() throws RepositoryException {
        String NAME = "TEST_NAME";
        TagDto tagDto = new TagDto();
        tagDto.setName(NAME);

        DtoUtil.filterFields(tagDto, null);

        assertThat(tagDto.getName(), is(NAME));
    }
}
