package app.user;

import app.config.DatabaseConfig;
import org.junit.Before;
import org.junit.Test;
import support.database.DatabaseMigrator;
import support.database.DriverManagerDataSource;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
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
        User user = new User("Some name", "an-email@gmail.com");
        String id = userRepository.create(user);

        assertThat(id, notNullValue());
    }

    @Test
    public void canFindUserByEmail() throws Exception {
        userRepository.create(new User("Some name", "someuser@email.com"));

        User user = userRepository.findByEmail("someuser@email.com");

        assertThat(user.email(), equalTo("someuser@email.com"));
        assertThat(user.name(), equalTo("Some name"));
    }
}