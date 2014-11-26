package org.robbins.flashcards.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.robbins.flashcards.client.util.ResourceUrls;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;


@Component
public class DefaultFlashcardClient extends AbstractCrudClient<FlashCardDto> implements FlashcardClient {
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

        String tagNames = "";
        for (TagDto tag : tags)
        {
            if (tagNames.length() == 0)
            {
                tagNames = tag.getId().toString();
            }
            else
            {
                tagNames += "," + tag.getId().toString();
            }
        }
        uriVariables.put("tags", tagNames);
        if (page != null)
        {
            uriVariables.put("page", Integer.toString(page.getPageNumber()));
            uriVariables.put("size", Integer.toString(page.getPageSize()));
        }

        return Arrays.asList(searchEntities(searchUrl(), uriVariables,
                FlashCardDto[].class));
    }

    private Map<String, String> setupSearchUriVariables() {
        Map<String, String> searchUriVariables = new HashMap<String, String>();

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
}
