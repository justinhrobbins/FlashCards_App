package org.robbins.flashcards.cassandra.repository;


import com.google.common.collect.Lists;
import org.junit.Test;
import org.robbins.flashcards.cassandra.repository.domain.UserCassandraBuilder;
import org.robbins.flashcards.cassandra.repository.domain.UserCassandraEntity;
import org.robbins.flashcards.repository.UserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class UserRepositoryIT extends AbstractCassandraIntegrationTest {

    private final UUID ID = UUID.fromString("9caa6c8e-b720-11e4-a71e-12e3f512a338");
    private final String OPEN_ID = "apiuser";

    @Inject
    private UserRepository<UserCassandraEntity, UUID> repository;

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
        UserCassandraEntity user = new UserCassandraBuilder().withId(UUID.randomUUID()).withOpenId(OPEN_ID).build();

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
