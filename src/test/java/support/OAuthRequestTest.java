package support;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OAuthRequestTest {

    @Test
    public void canRead() throws Exception {
        OAuthRequest oAuthRequest = new OAuthRequest("secret", "key", false);
        Optional<String> response = oAuthRequest.read("https://marketplace.appdirect.com/api/integration/v1/events/dummyOrder");

        assertThat(response.isPresent(), is(true));
    }
}