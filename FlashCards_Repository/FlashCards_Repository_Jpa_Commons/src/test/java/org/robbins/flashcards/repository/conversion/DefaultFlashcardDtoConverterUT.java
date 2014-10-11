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
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.repository.conversion.impl.DefaultFlashcardDtoConverter;
import org.robbins.tests.BaseMockingTest;
import org.springframework.test.util.ReflectionTestUtils;

public class DefaultFlashcardDtoConverterUT extends BaseMockingTest {
    private DtoConverter converter;
    private FlashCard flashCard;
    private FlashCardDto flashCardDto;
    private final String QUESTION = "question";
    private final String ANSWER = "answer";

    @Mock
    private Mapper mockMapper;

    @Before
    public void setup()
    {
        converter = new DefaultFlashcardDtoConverter();
        ReflectionTestUtils.setField(converter, "mapper", mockMapper);

        flashCard = new FlashCard();
        flashCard.setQuestion(QUESTION);
        flashCard.setAnswer(ANSWER);

        flashCardDto = new FlashCardDto();
        flashCardDto.setQuestion(QUESTION);
        flashCardDto.setAnswer(ANSWER);
    }

    @Test
    public void getEntities() {
        List<FlashCardDto> flashCardDtos = Arrays.asList(flashCardDto);
        Mockito.when(mockMapper.map(flashCardDto, FlashCard.class)).thenReturn(flashCard);

        List<FlashCard> results = converter.getEtnties(flashCardDtos);

        Assert.assertThat(results, CoreMatchers.is(List.class));
        Mockito.verify(mockMapper).map(flashCardDto, FlashCard.class);
    }
}
