package org.robbins.flashcards.client;

import org.robbins.flashcards.client.util.ResourceUrls;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.ServiceException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultUserClient extends AbstractCrudClient<UserDto, String> implements UserClient {

    @Override
    public String getEntityListUrl() {
        return getServerAddress() + ResourceUrls.users;
    }

    @Override
    public String getEntityUrl() {
        return getServerAddress() + ResourceUrls.user;
    }

    @Override
    public String postEntityUrl() {
        return getServerAddress() + ResourceUrls.users;
    }

    @Override
    public String postBulkEntitiesUrl() {
        return getServerAddress() + ResourceUrls.users + BULK;
    }

    @Override
    public String putEntityUrl() {
        return getServerAddress() + ResourceUrls.user;
    }

    @Override
    public String deleteEntityUrl() {
        return getServerAddress() + ResourceUrls.user;
    }

    @Override
    public String searchUrl() {
        return getServerAddress() + ResourceUrls.usersSearch;
    }

    @Override
    public String updateUrl() {
        return getServerAddress() + ResourceUrls.userUpdate;
    }

    @Override
    public Class<UserDto> getClazz() {
        return UserDto.class;
    }

    @Override
    public Class<UserDto[]> getClazzArray() {
        return UserDto[].class;
    }

    @Override
    public UserDto findUserByOpenid(final String openid) throws ServiceException {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("openid", openid);

        return searchSingleEntity(uriVariables);
    }
}
