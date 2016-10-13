package app.account;

import app.config.DatabaseConfig;
import org.junit.Before;
import org.junit.Test;
import support.database.DatabaseMigrator;
import support.database.DriverManagerDataSource;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class DatabaseAccountRepositoryTest {

    private AccountRepository accountRepository;

    @Before
    public void createDatabase() throws Exception {
        DataSource dataSource = new DriverManagerDataSource(new DatabaseConfig("jdbc:h2:mem:musicals;DB_CLOSE_DELAY=-1", "admin", "admin"));
        DatabaseMigrator databaseMigrator = new DatabaseMigrator(dataSource);
        databaseMigrator.migrate();

        accountRepository = new DatabaseAccountRepository(dataSource);
    }

    @Test
    public void canCreateAccount() throws Exception {
        assertThat(accountRepository.create(Account.PricingModel.Free), not(nullValue()));
    }

    @Test
    public void canFindAccount() throws Exception {
        String id = accountRepository.create(Account.PricingModel.Free);

        Account account = accountRepository.find(id);
        assertThat(account.pricingModel(), is(Account.PricingModel.Free));
    }
}