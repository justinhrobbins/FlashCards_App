package org.robbins.flashcards.client;

import org.robbins.flashcards.client.util.ResourceUrls;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.exceptions.ServiceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component("flashcardClient")
public class DefaultFlashCardClient extends AbstractCrudClient<FlashCardDto, Long> implements FlashCardClient
{
    @Override
    public String getEntityListUrl() {
        return getServerAddress() + ResourceUrls.flashCards;
    }

    @Override
    public String getEntityUrl() {
        return getServerAddress() + ResourceUrls.flashCard;
    }

    @Override
    public String postEntityUrl() {
        return getServerAddress() + ResourceUrls.flashCards;
    }

    @Override
    public String postBatchEntitiesUrl() {
        return getServerAddress() + ResourceUrls.flashCards + ResourceUrls.batch;
    }

    @Override
    public String putEntityUrl() {
        return getServerAddress() + ResourceUrls.flashCard;
    }

    @Override
    public String deleteEntityUrl() {
        return getServerAddress() + ResourceUrls.flashCard;
    }

    @Override
    public String searchUrl() {
        return getServerAddress() + ResourceUrls.flashCardsSearch;
    }

    @Override
    public Class<FlashCardDto> getClazz() {
        return FlashCardDto.class;
    }

    @Override
    public Class<FlashCardDto[]> getClazzArray() {
        return FlashCardDto[].class;
    }

    @Override
    public List<FlashCardDto> findByTagsIn(Set<TagDto> tags) throws ServiceException {
        return findByTagsIn(tags, null);
    }

    @Override
    public String updateUrl() {
        return getServerAddress() + ResourceUrls.flashCardUpdate;
    }

    @Override
    public List<FlashCardDto> findByTagsIn(final Set<TagDto> tags, final PageRequest page) throws ServiceException {
        final Map<String, String> uriVariables = setupSearchUriVariables();

        final String tagNames = tags.stream()
                .map(tag -> tag.getId().toString())
                .collect(Collectors.joining(","));

        uriVariables.put("tags", tagNames);
        if (page != null) {
            uriVariables.put("page", Integer.toString(page.getPageNumber()));
            uriVariables.put("size", Integer.toString(page.getPageSize()));
        }

        return Arrays.asList(searchEntities(searchUrl(), uriVariables,
                FlashCardDto[].class));
    }

    private Map<String, String> setupSearchUriVariables() {
        final Map<String, String> searchUriVariables = new HashMap<String, String>();

        searchUriVariables.put("question", "");
        searchUriVariables.put("tags", "");

        return searchUriVariables;
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question) throws ServiceException {
        return findByQuestionLike(question, null);
    }

    @Override
    public List<FlashCardDto> findByQuestionLike(final String question, final PageRequest page) throws ServiceException {
        final Map<String, String> uriVariables = new HashMap<>();

        uriVariables.put("question", question);
        if (page != null)
        {
            uriVariables.put("page", Integer.toString(page.getPageNumber()));
            uriVariables.put("size", Integer.toString(page.getPageSize()));
        }
        return Arrays.asList(searchEntities(searchUrl(), uriVariables,
                FlashCardDto[].class));
    }

    @Override
    public FlashCardDto findByQuestion(final String question) throws ServiceException {
        return findByQuestionLike(question, null).get(0);
    }

    @Override
    public List<FlashCardDto> findFlashCardsForTag(Long tagId, Set<String> fields) throws FlashCardsException
    {
        final Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("tagId", String.valueOf(tagId));
        return Arrays.asList(searchEntities(getServerAddress() + ResourceUrls.flashCardsForTag, uriVariables, FlashCardDto[].class));
    }
}
