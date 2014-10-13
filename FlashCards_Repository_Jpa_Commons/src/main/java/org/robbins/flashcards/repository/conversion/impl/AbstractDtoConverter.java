package org.robbins.flashcards.repository.conversion.impl;

import javax.inject.Inject;

import org.dozer.Mapper;

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
