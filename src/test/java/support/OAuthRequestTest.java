package support;

import org.junit.Test;

public class OAuthRequestTest {

    @Test
    public void canRead() throws Exception {
        OAuthRequest oAuthRequest = new OAuthRequest("secret", "key", false);
        oAuthRequest.read("https://marketplace.appdirect.com/api/integration/v1/events/dummyOrder");
    }
}