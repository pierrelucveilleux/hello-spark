package support;

import org.junit.Before;
import org.junit.Test;
import spark.Request;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OAuthRequestTest {

    private Request request;

    @Before
    public void aRequest() throws Exception {
        request = mock(Request.class);
        when(request.queryParams("eventUrl")).thenReturn("https://marketplace.appdirect.com/api/integration/v1/events/dummyOrder");

    }

    @Test
    public void canSign() throws Exception {
        OAuthRequest oAuthRequest = new OAuthRequest("secret", "key");
        oAuthRequest.sign(request);
    }
}