package app.user;

import app.config.DatabaseConfig;
import org.junit.Before;
import org.junit.Test;
import support.database.DatabaseMigrator;
import support.database.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class DatabaseUserRepositoryTest {

    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        DataSource dataSource = new DriverManagerDataSource(new DatabaseConfig("jdbc:h2:mem:musicals;DB_CLOSE_DELAY=-1", "admin", "admin"));
        DatabaseMigrator databaseMigrator = new DatabaseMigrator(dataSource);
        databaseMigrator.migrate();

        userRepository = new DatabaseUserRepository(dataSource);
    }

    @Test
    public void canCreateUser() throws Exception {
        String id = userRepository.create(someUser());

        assertThat(id, notNullValue());
    }

    @Test
    public void canFindUserByEmail() throws Exception {
        String uuid = userRepository.create(someUser());

        User user = userRepository.findByEmail("an-email@gmail.com");

        assertThat(user.uuid(), equalTo(uuid));
        assertThat(user.openid(), equalTo("82fdb185-10e7-4780-a5e6-69a3e74c9eec"));
        assertThat(user.firstName(), equalTo("first"));
        assertThat(user.lastName(), equalTo("last"));
        assertThat(user.email(), equalTo("an-email@gmail.com"));
    }

    @Test
    public void returnsUserIfUserCanBeFindByUuid() throws Exception {
        String uuid = userRepository.create(someUser());

        User user = userRepository.findByUuid(uuid);

        assertThat(user.uuid(), equalTo(uuid));
        assertThat(user.openid(), equalTo("82fdb185-10e7-4780-a5e6-69a3e74c9eec"));
    }

    @Test
    public void returnsNullIfUserCannotBeFindByUuid() throws Exception {
        User user = userRepository.findByUuid("inexisting");

        assertThat(user, nullValue());
    }

    private User someUser() {
        return new User(UUID.randomUUID().toString(), "82fdb185-10e7-4780-a5e6-69a3e74c9eec", "first", "last", "an-email@gmail.com");
    }
}