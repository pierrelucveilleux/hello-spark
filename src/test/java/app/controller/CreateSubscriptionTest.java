package app.controller;

import app.account.AccountRepository;
import app.account.PricingModel;
import app.user.User;
import app.user.UserRepository;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import support.OAuthRequest;

import static app.user.UserMatcher.user;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static support.ContentOf.resource;

public class CreateSubscriptionTest {

    CreateSubscription subscribe;
    AccountRepository accountRepository;
    UserRepository userRepository;
    OAuthRequest signRequest;
    Request request;
    Response response;

    @Before
    public void aSubscriptionRequest() throws Exception {
        request = mock(Request.class);
        response = mock(Response.class);

        signRequest = mock(OAuthRequest.class);
        accountRepository = mock(AccountRepository.class);
        userRepository = mock(UserRepository.class);

        subscribe = new CreateSubscription(accountRepository, userRepository, signRequest, new Gson());
    }

    @Test
    public void isSignedAndReadFromEventUrlParam() throws Exception {
        when(request.queryParams("eventUrl")).thenReturn("http://appdirect.com/event/1");

        subscribe.handle(request, response);

        verify(signRequest).read("http://appdirect.com/event/1");
    }

    @Test
    public void returnsAnErrorWhenEventCannotBeRead() throws Exception {
        when(signRequest.read(anyString())).thenReturn(empty());

        String result = (String)subscribe.handle(request, response);

        assertThat(result, hasJsonPath("success", equalTo(false)));
        assertThat(result, hasJsonPath("errorCode", equalTo("INTERNAL_ERROR")));
        assertThat(result, hasJsonPath("errorMessage", equalTo("Cannot read subscription")));
    }

    @Test
    public void createSubscriptionInRepository() throws Exception {
        when(request.queryParams("eventUrl")).thenReturn("http://appdirect.com/event/1");
        when(signRequest.read(anyString())).thenReturn(of(resource("subscription/create.json")));

        subscribe.handle(request, response);

        verify(accountRepository).create(PricingModel.Free);
    }

    @Test
    public void createUserInRepository() throws Exception {
        when(request.queryParams("eventUrl")).thenReturn("http://appdirect.com/event/1");
        when(signRequest.read(anyString())).thenReturn(of(resource("subscription/create.json")));
        when(accountRepository.create(eq(PricingModel.Free))).thenReturn("12345");

        subscribe.handle(request, response);

        verify(userRepository).create(argThat(user(someUser("12345"))));
    }

    @Test
    public void returnsAccountIdentifierOnSuccess() throws Exception {
        when(request.queryParams("eventUrl")).thenReturn("http://appdirect.com/event/1");
        when(signRequest.read(anyString())).thenReturn(of(resource("subscription/create.json")));
        when(accountRepository.create(eq(PricingModel.Free))).thenReturn("12345");

        String result = (String)subscribe.handle(request, response);

        assertThat(result, hasJsonPath("success", equalTo(true)));
        assertThat(result, hasJsonPath("accountIdentifier", equalTo("12345")));
    }

    private User someUser(String accountId) {
        User user = new User("ec5d8eda-5cec-444d-9e30-125b6e4b67e2", "DummyCreatorFirst", "DummyCreatorLast", "test-email+creator@appdirect.com");
        user.accountId(accountId);
        return user;
    }
}