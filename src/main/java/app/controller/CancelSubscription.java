package app.controller;

import app.account.AccountRepository;
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
import static app.domain.ApiResult.succes;

public class CancelSubscription implements Route {

    private Logger logger = LoggerFactory.getLogger(CancelSubscription.class);

    private final AccountRepository accountRepository;
    private final OAuthRequest oAuthRequest;
    private final Gson gson;

    public CancelSubscription(AccountRepository accountRepository, OAuthRequest oAuthRequest, Gson gson) {
        this.accountRepository = accountRepository;
        this.oAuthRequest = oAuthRequest;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LogRequest.logRequestInfo(request, logger);

        response.type("application/json");

        Optional<String> body = oAuthRequest.read(request.queryParams("eventUrl"));
        if(body.isPresent()) {
            SubsciptionReader subsciptionReader = new SubsciptionReader(gson);
            logger.info("Read event", body.get());
            SubscriptionEvent subscriptionEvent = subsciptionReader.read(body.get());

            String accountId = subscriptionEvent.payload.order.accountIdentifier;
            accountRepository.remove(accountId);

            logger.info("Removed account: " + accountId +  ", Body: " + body);
            return gson.toJson(succes(accountId));
        } else {
            return gson.toJson(error("INTERNAL_ERROR", "Cannot read subscription"));
        }
    }
}
