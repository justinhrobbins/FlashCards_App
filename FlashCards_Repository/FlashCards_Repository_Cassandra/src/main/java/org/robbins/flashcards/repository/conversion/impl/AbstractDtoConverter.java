package org.robbins.flashcards.repository.conversion.impl;

import org.dozer.Mapper;

import javax.inject.Inject;

public class AbstractDtoConverter {
    @Inject
    private Mapper mapper;

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(final Mapper mapper) {
        this.mapper = mapper;
    }
}
