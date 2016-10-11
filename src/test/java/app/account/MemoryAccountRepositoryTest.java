package app.account;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MemoryAccountRepositoryTest  {

    private AccountRepository accountRepository;
    private String someAccount;

    @Before
    public void setUp() throws Exception {
        accountRepository = new MemoryAccountRepository();
        someAccount = accountRepository.create(Account.PricingModel.Free);
    }

    @Test
    public void findAnAccountById() throws Exception {
        Account account = accountRepository.find(someAccount);
        assertThat(account, not(nullValue()));
    }
}