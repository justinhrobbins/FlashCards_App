package org.robbins.flashcards.cassandra.repository;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.domain.UserCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.UserCassandraEntity;
import org.robbins.flashcards.repository.UserRepository;

import com.google.common.collect.Lists;

public class UserRepositoryIT extends AbstractCassandraIntegrationTest {

    private final Long ID = 1L;
    private final String OPEN_ID = "apiuser";

    @Inject
    private UserRepository<UserCassandraEntity, Long> repository;

    @Test
    public void testFindOne() {
        UserCassandraEntity result = repository.findOne(ID);
        assertThat(result.getId(), is(ID));
        assertThat(result.getOpenid(), is(OPEN_ID));
    }

    @Test
    public void testFindAll() {
        List<UserCassandraEntity> users = Lists.newArrayList(repository.findAll());
        assertThat(users.size(), greaterThan(0));
    }

    @Test
    public void insertTag() {
        UserCassandraEntity user = new UserCassandraBuilder().withId(RandomUtils.nextLong(0, Long.MAX_VALUE)).withOpenId(OPEN_ID).build();

        UserCassandraEntity result = repository.save(user);
        assertThat(result.getId(), is(user.getId()));
        assertThat(result.getOpenid(), is(user.getOpenid()));
    }

    @Test
    public void testFindUserByOpenid() {
        UserCassandraEntity result = repository.findUserByOpenid(OPEN_ID);
        assertThat(result.getOpenid(), is(OPEN_ID));
    }
}
