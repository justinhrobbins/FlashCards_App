
package org.robbins.flashcards.cassandra.repository.facade;

import org.robbins.flashcards.cassandra.repository.domain.UserCassandraEntity;
import org.robbins.flashcards.cassandra.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.UUID;

@Component("userRepositoryFacade")
public class DefaultUserRepositoryFacade extends AbstractCrudRepositoryFacadeImpl<UserDto, UserCassandraEntity>  implements
        UserFacade {

    @Inject
	private UserRepository<UserCassandraEntity, Long> repository;

    @Inject
    @Qualifier("userDtoConverter")
    private DtoConverter<UserDto, UserCassandraEntity> converter;

    public DtoConverter<UserDto, UserCassandraEntity> getConverter()
    {
        return converter;
    }

	public UserRepository<UserCassandraEntity, Long> getRepository() {
		return repository;
	}

    @Override
    public UserDto findUserByOpenid(final String openid) throws FlashcardsException {
        UserCassandraEntity user = repository.findUserByOpenid(openid);
        return user == null ? null : getConverter().getDto(user);
    }

}
