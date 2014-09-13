package org.robbins.flashcards.conversion;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robbins.flashcards.conversion.impl.DefaultFlashcardDtoConverter;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.tests.BaseMockingTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(mockMapper.map(flashCardDto, FlashCard.class)).thenReturn(flashCard);

        List<FlashCard> results = converter.getEtnties(flashCardDtos);

        assertThat(results, is(List.class));
        verify(mockMapper).map(flashCardDto, FlashCard.class);
    }
}
