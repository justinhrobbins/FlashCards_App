
package org.robbins.flashcards.repository.springdata;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.robbins.flashcards.model.User;
import org.robbins.flashcards.repository.UserRepository;
import org.robbins.tests.BaseIntegrationTest;
import org.robbins.tests.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration("classpath:test-applicationContext-repository-springdata.xml")
@DatabaseSetup("classpath:test-flashCardsAppRepository.xml")
@Category(IntegrationTest.class)
public class UserRepositoryIT extends BaseIntegrationTest {

    private static final String OPEN_ID = "my-open-id";

    @Inject
    private UserRepository<User, String> userRepository;

    @Test
    public void findByOpenId() {
        final User result = userRepository.findUserByOpenid(OPEN_ID);
        assertThat(result, is(instanceOf(User.class)));
        assertThat(result.getOpenid(), is(OPEN_ID));
    }
}
