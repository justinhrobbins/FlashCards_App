
package org.robbins.flashcards.cassandra.repository.facade;

import org.robbins.flashcards.cassandra.repository.domain.UserCassandraEntity;
import org.robbins.flashcards.cassandra.repository.facade.base.AbstractCrudRepositoryFacadeImpl;
import org.robbins.flashcards.conversion.DtoConverter;
import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.exceptions.FlashCardsException;
import org.robbins.flashcards.facade.UserFacade;
import org.robbins.flashcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("userRepositoryFacade")
public class DefaultUserRepositoryFacade extends AbstractCrudRepositoryFacadeImpl<UserDto, UserCassandraEntity>  implements
        UserFacade {

    @Inject
	private UserRepository<UserCassandraEntity, Long> repository;

    @Inject
    @Qualifier("userDtoConverter")
    private DtoConverter<UserDto, UserCassandraEntity> converter;

    @Override
    public DtoConverter<UserDto, UserCassandraEntity> getConverter()
    {
        return converter;
    }

    @Override
    public UserRepository<UserCassandraEntity, Long> getRepository() {
		return repository;
	}

    @Override
    public UserDto findUserByOpenid(final String openid) throws FlashCardsException
    {
        UserCassandraEntity user = repository.findUserByOpenid(openid);
        return user == null ? null : getConverter().getDto(user);
    }

}
