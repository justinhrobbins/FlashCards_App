package org.robbins.load.tester.util;


import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.robbins.flashcards.dto.AbstractAuditableDto;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.builder.FlashCardDtoBuilder;
import org.robbins.flashcards.dto.builder.TagDtoBuilder;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class LoadingTestingUtil {

    private static final String prefix = "load-tester";
    private static final String tagDto = "TagDto";
    private static final String flashcardDto = "FlashCardDto";

    public static TagDto createTagDto(final String name) {
        return new TagDtoBuilder().withName(name).build();
    }

    public static FlashCardDto createFlashcardDto(final String question) {
        return new FlashCardDtoBuilder()
                .withQuestion(question)
                .withAnswer(RandomStringUtils.randomAlphabetic(10))
                .build();
    }

    public static AbstractAuditableDto createDto(final String name, final Class<? extends AbstractAuditableDto> dtoClass) {
        if (dtoClass.getSimpleName().equals(tagDto)) {
            return createTagDto(name);
        } else if (dtoClass.getSimpleName().equals(flashcardDto)) {
            return createFlashcardDto(name);
        } else {
            throw new IllegalArgumentException("dtoClass does not match expected: " + dtoClass.getSimpleName());
        }
    }

    public static List<AbstractAuditableDto> createDtos(final int totalSize, final Class<? extends AbstractAuditableDto> dtoClass) {
        return LongStream.range(1, totalSize + 1)
                .mapToObj(i -> createDto(buildName(i), dtoClass))
                .collect(Collectors.toList());
    }

    public static List<List<AbstractAuditableDto>> createDtosInBatches(final int totalSize, final int batchSize, final Class<? extends AbstractAuditableDto> dtoClass) {
        final List<AbstractAuditableDto> dtos = LoadingTestingUtil.createDtos(totalSize, dtoClass);
        return Lists.partition(dtos, batchSize);
    }

    private static String buildName(final long suffix) {
        return prefix + "-" + UUID.randomUUID().toString() + "-" + suffix;
    }

    public static Long calculateLoadingDuration(final Date start, final Date end) {
        return end.getTime() - start.getTime();
    }
}
