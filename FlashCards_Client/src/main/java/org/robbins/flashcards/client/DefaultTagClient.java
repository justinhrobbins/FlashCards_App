package org.robbins.flashcards.client;

import org.robbins.flashcards.client.util.ResourceUrls;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("tagClient")
public class DefaultTagClient extends AbstractCrudClient<TagDto, String> implements TagClient {

    @Override
    public String getEntityListUrl() {
        return getServerAddress() + ResourceUrls.tags;
    }

    @Override
    public String getEntityUrl() {
        return getServerAddress() + ResourceUrls.tag;
    }

    @Override
    public String postEntityUrl() { return getServerAddress() + ResourceUrls.tags; }

    @Override
    public String postBatchEntitiesUrl() {
        return getServerAddress() + ResourceUrls.tags + ResourceUrls.batch;
    }

    @Override
    public String putEntityUrl() {
        return getServerAddress() + ResourceUrls.tag;
    }

    @Override
    public String deleteEntityUrl() {
        return getServerAddress() + ResourceUrls.tag;
    }

    @Override
    public String searchUrl() {
        return getServerAddress() + ResourceUrls.tagsSearch;
    }

    @Override
    public String updateUrl() {
        return getServerAddress() + ResourceUrls.tagUpdate;
    }

    @Override
    public Class<TagDto> getClazz() {
        return TagDto.class;
    }

    @Override
    public Class<TagDto[]> getClazzArray() {
        return TagDto[].class;
    }

    @Override
    public TagDto findByName(final String name) throws ServiceException {
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("name", name);
        return searchSingleEntity(uriVariables);
    }

    @Override
    public List<TagDto> findTagsForFlashcard(final String flashcardId, final Set<String> fields) {
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("flashcardId", String.valueOf(flashcardId));
        return Arrays.asList(searchEntities(getServerAddress() + ResourceUrls.tagsForFlashcard, uriVariables, TagDto[].class));
    }
}
