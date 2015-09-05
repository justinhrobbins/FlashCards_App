package org.robbins.load.tester.util;


import com.google.common.collect.Lists;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.dto.builder.TagDtoBuilder;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class LoadingTestingUtil {

    public static final String prefix = "load-tester";

    public static TagDto createTagDto(final String name) {
        return new TagDtoBuilder().withName(name).build();
    }

    public static List<TagDto> createTagDtos(final int totalSize) {
        return LongStream.range(1, totalSize + 1)
                .mapToObj(i -> createTagDto(buildName(i)))
                .collect(Collectors.toList());
    }

    public static List<List<TagDto>> createTagDtosInBatches(final int totalSize, final int batchSize) {
        final List<TagDto> tags = LoadingTestingUtil.createTagDtos(totalSize);
        return Lists.partition(tags, batchSize);
    }

    private static String buildName(final long suffix) {
        return prefix + "-" + UUID.randomUUID().toString() + "-" + suffix;
    }

    public static Long calculateLoadingDuration(final Date start, final Date end) {
        return end.getTime() - start.getTime();
    }
}
