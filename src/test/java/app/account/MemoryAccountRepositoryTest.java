package app.account;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MemoryAccountRepositoryTest  {

    private AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
        accountRepository = new MemoryAccountRepository();
        accountRepository.create("some-id", "Some Name");
    }

    @Test
    public void findAnAccountById() throws Exception {
        Account account = accountRepository.findAccount("some-id");
        assertThat(account, not(nullValue()));
    }
}