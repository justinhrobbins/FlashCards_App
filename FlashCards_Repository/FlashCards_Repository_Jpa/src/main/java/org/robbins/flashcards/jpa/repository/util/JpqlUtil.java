package org.robbins.flashcards.jpa.repository.util;

import org.springframework.data.domain.Sort;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class JpqlUtil {

    public static String sortAsString(final Sort sort) {
        return StreamSupport.stream(sort.spliterator(), false)
                .map(order -> order.getProperty() + " " + order.getDirection())
                .collect(Collectors.joining(", "));
    }
}
