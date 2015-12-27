package org.robbins.flashcards.dto.builder;

import org.robbins.flashcards.dto.TagDto;

public class TagDtoBuilder {

    private TagDto tag = new TagDto();

    public TagDtoBuilder() {}

    public TagDtoBuilder withId(final Long id) {
        tag.setId(id);
        return this;
    }

    public TagDtoBuilder withName(final String name) {
        tag.setName(name);
        return this;
    }

    public TagDto build() {
        return tag;
    }
}
