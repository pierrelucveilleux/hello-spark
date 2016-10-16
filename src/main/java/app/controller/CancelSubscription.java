package app.controller;

import app.account.AccountRepository;
import app.assignment.UserAssignmentRepository;
import app.marketplace.subscription.SubsciptionReader;
import app.marketplace.subscription.SubscriptionEvent;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import support.LogRequest;
import support.OAuthRequest;

import java.util.Optional;

import static app.domain.ApiResult.error;
import static app.domain.ApiResult.succesAccount;

public class CancelSubscription implements Route {

    private Logger logger = LoggerFactory.getLogger(CancelSubscription.class);

    private final AccountRepository accountRepository;
    private final UserAssignmentRepository userAssignmentRepository;
    private final OAuthRequest oAuthRequest;
    private final Gson gson;

    public CancelSubscription(AccountRepository accountRepository, UserAssignmentRepository userAssignmentRepository, OAuthRequest oAuthRequest, Gson gson) {
        this.accountRepository = accountRepository;
        this.userAssignmentRepository = userAssignmentRepository;
        this.oAuthRequest = oAuthRequest;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LogRequest.logRequestInfo(request, logger);

        response.type("application/json");

        Optional<String> body = oAuthRequest.read(request.queryParams("eventUrl"));
        if(body.isPresent()) {
            logger.info("Read event", body.get());
            SubscriptionEvent subscriptionEvent = new SubsciptionReader(gson).read(body.get());

            String accountId = subscriptionEvent.payload.account.accountIdentifier;
            userAssignmentRepository.unassignAllUsers(accountId);
            accountRepository.remove(accountId);

            logger.info("Removed account: " + accountId +  ", Body: " + body);
            return gson.toJson(succesAccount(accountId));
        } else {
            return gson.toJson(error("INTERNAL_ERROR", "Cannot read subscription"));
        }
    }
}
