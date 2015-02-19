
package org.robbins.flashcards.cassandra.repository.facade;

import org.apache.commons.lang3.NotImplementedException;
import org.robbins.flashcards.cassandra.repository.UserRepository;
import org.robbins.flashcards.cassandra.repository.domain.UserCassandraDto;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.UserFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Component("userRepositoryFacade")
public class DefaultUserRepositoryFacade implements
        UserFacade {

    @Inject
	private UserRepository repository;

    @Inject
    @Qualifier("userDtoConverter")
    private DtoConverter<UserDto, UserCassandraDto> converter;

    public DtoConverter<UserDto, UserCassandraDto> getConverter()
    {
        return converter;
    }

	public UserRepository getRepository() {
		return repository;
	}

    @Override
    public UserDto findUserByOpenid(final String openid) throws FlashcardsException {
        UserCassandraDto user = repository.findUserByOpenid(openid);
        return getConverter().getDto(user);
    }

    @Override
    public List<UserDto> list() throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<UserDto> list(final Integer page, final Integer size, final String sort, final String direction) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<UserDto> list(final Integer page, final Integer size, final String sort, final String direction, final Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public Long count() {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public UserDto findOne(final String s) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public UserDto findOne(final String s, final Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public UserDto save(final UserDto entity) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public void delete(final String s) {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }

    @Override
    public List<UserDto> findByCreatedBy(final String userId, final Set<String> fields) throws FlashcardsException {
        throw new NotImplementedException("method not yet implemented in Cassandra repository");
    }
}
