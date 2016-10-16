package app.assignment;

import app.account.AccountRepository;
import app.account.DatabaseAccountRepository;
import app.account.PricingModel;
import app.config.DatabaseConfig;
import app.user.DatabaseUserRepository;
import app.user.User;
import app.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import support.database.DatabaseMigrator;
import support.database.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DatabaseUserAssignmentRepositoryTest {

    UserAssignmentRepository userAssignmentRepository;
    UserRepository userRepository;
    AccountRepository accountRepository;
    String accountId;
    String userId;

    @Before
    public void createDatabase() throws Exception {
        DataSource dataSource = new DriverManagerDataSource(new DatabaseConfig("jdbc:h2:mem:musicals;DB_CLOSE_DELAY=-1", "admin", "admin"));
        DatabaseMigrator databaseMigrator = new DatabaseMigrator(dataSource);
        databaseMigrator.migrate();

        accountRepository = new DatabaseAccountRepository(dataSource);
        userRepository = new DatabaseUserRepository(dataSource);
        userAssignmentRepository = new DatabaseUserAssignmentRepository(dataSource);

        accountId = accountRepository.create(PricingModel.Free);
        userId = userRepository.create(someUser());
    }

    @Test
    public void canAssignUserToAccount() throws Exception {

        UserAssignment assignment = new UserAssignment(userId, accountId);

        assertThat(userAssignmentRepository.assign(assignment), is(true));
    }

    @Test
    public void returnsFalseIfUserIsNotAssignedToAccount() throws Exception {

        UserAssignment assignment = new UserAssignment(userId, accountId);

        assertThat(userAssignmentRepository.isAssigned(assignment), is(false));
    }

    @Test
    public void returnsTrueIfUserIsAssignedToAccount() throws Exception {

        UserAssignment assignment = new UserAssignment(userId, accountId);
        userAssignmentRepository.assign(assignment);

        assertThat(userAssignmentRepository.isAssigned(assignment), is(true));
    }

    @Test
    public void canUnassignUserToAccount() throws Exception {

        UserAssignment assignment = new UserAssignment(userId, accountId);
        userAssignmentRepository.assign(assignment);

        assertThat(userAssignmentRepository.unassign(assignment), is(true));
    }

    @Test
    public void canUnassignAllUsersFromAccount() throws Exception {

        UserAssignment assignment = new UserAssignment(userId, accountId);
        userAssignmentRepository.assign(assignment);

        String otherUserId = userRepository.create(someUser());
        UserAssignment otherAssignment = new UserAssignment(otherUserId, accountId);
        userAssignmentRepository.assign(otherAssignment);

        assertThat(userAssignmentRepository.unassignAllUsers(accountId), is(true));
        assertThat(userAssignmentRepository.isAssigned(assignment), is(false));
        assertThat(userAssignmentRepository.isAssigned(otherAssignment), is(false));
    }

    public User someUser() {
        return new User(UUID.randomUUID().toString(), "openid", "first", "last", "email");
    }
}