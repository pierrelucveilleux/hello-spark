package app.controller;

import app.account.AccountRepository;
import app.assignment.UserAssignmentRepository;
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
import static org.mockito.Mockito.*;
import static support.ContentOf.resource;

public class CancelSubscriptionTest {

    CancelSubscription subscribe;
    AccountRepository accountRepository;
    UserAssignmentRepository userAssignmentRepository;
    OAuthRequest oAuthRequest;
    Request request;
    Response response;

    @Before
    public void aSubscriptionRequest() throws Exception {
        request = mock(Request.class);
        response = mock(Response.class);

        oAuthRequest = mock(OAuthRequest.class);
        accountRepository = mock(AccountRepository.class);
        userAssignmentRepository = mock(UserAssignmentRepository.class);
        subscribe = new CancelSubscription(accountRepository, userAssignmentRepository, oAuthRequest, new Gson());
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
    public void removeSubscriptionInRepository() throws Exception {
        when(request.queryParams("eventUrl")).thenReturn("http://appdirect.com/event/1");
        when(oAuthRequest.read(anyString())).thenReturn(of(resource("subscription/cancel.json")));

        subscribe.handle(request, response);

        verify(accountRepository).remove("7aa211cc-f0d4-457c-b53b-0d3f13d5a24b");
    }

    @Test
    public void unasignedUserWhenAccountIsCancelled() throws Exception {
        when(request.queryParams("eventUrl")).thenReturn("http://appdirect.com/event/1");
        when(oAuthRequest.read(anyString())).thenReturn(of(resource("subscription/cancel.json")));

        subscribe.handle(request, response);

        verify(userAssignmentRepository).unassignAllUsers("7aa211cc-f0d4-457c-b53b-0d3f13d5a24b");
    }
}