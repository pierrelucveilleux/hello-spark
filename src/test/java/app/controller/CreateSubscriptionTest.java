package app.controller;

import app.account.Account;
import app.account.AccountRepository;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import support.OAuthRequest;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static support.ContentOf.resource;

public class CreateSubscriptionTest {

    CreateSubscription subscribe;
    AccountRepository accountRepository;
    OAuthRequest oAuthRequest;
    Request request;
    Response response;

    @Before
    public void aSubscriptionRequest() throws Exception {
        request = mock(Request.class);
        response = mock(Response.class);

        oAuthRequest = mock(OAuthRequest.class);
        accountRepository = mock(AccountRepository.class);
        subscribe = new CreateSubscription(accountRepository, oAuthRequest, new Gson());
    }

    @Test
    public void isSignedAndReadFromEventUrlParam() throws Exception {
        when(request.queryParams("eventUrl")).thenReturn("http://appdirect.com/event/1");

        subscribe.handle(request, response);

        verify(oAuthRequest).read("http://appdirect.com/event/1");
    }

    @Test
    public void returnsAnErrorWhenEventCannotBeRead() throws Exception {
        when(oAuthRequest.read(anyString())).thenReturn(empty());

        String result = (String)subscribe.handle(request, response);

        assertThat(result, hasJsonPath("success", equalTo(false)));
        assertThat(result, hasJsonPath("errorCode", equalTo("INTERNAL_ERROR")));
        assertThat(result, hasJsonPath("errorMessage", equalTo("Cannot read subscription")));
    }

    @Test
    public void createSubscriptionInRepository() throws Exception {
        when(request.queryParams("eventUrl")).thenReturn("http://appdirect.com/event/1");
        when(oAuthRequest.read(anyString())).thenReturn(of(resource("subscription/create.json")));

        subscribe.handle(request, response);

        verify(accountRepository).create(Account.PricingModel.Free);
    }

    @Test
    public void returnsAccountIdentifierOnSuccess() throws Exception {
        when(request.queryParams("eventUrl")).thenReturn("http://appdirect.com/event/1");
        when(oAuthRequest.read(anyString())).thenReturn(of(resource("subscription/create.json")));
        when(accountRepository.create(eq(Account.PricingModel.Free))).thenReturn("12345");

        String result = (String)subscribe.handle(request, response);

        assertThat(result, hasJsonPath("success", equalTo(true)));
        assertThat(result, hasJsonPath("accountIdentifier", equalTo("12345")));
    }
}