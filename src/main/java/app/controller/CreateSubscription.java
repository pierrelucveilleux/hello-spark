package app.controller;

import app.account.AccountRepository;
import app.account.PricingModel;
import app.marketplace.subscription.Creator;
import app.marketplace.subscription.SubsciptionReader;
import app.marketplace.subscription.SubscriptionEvent;
import app.user.User;
import app.user.UserRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import support.LogRequest;
import support.OAuthRequest;

import java.util.Optional;

import static app.account.PricingModel.fromValue;
import static app.domain.ApiResult.error;
import static app.domain.ApiResult.succes;

public class CreateSubscription implements Route {

    private Logger logger = LoggerFactory.getLogger(CreateSubscription.class);

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final OAuthRequest oAuthRequest;
    private final Gson json;

    public CreateSubscription(AccountRepository accountRepository, UserRepository userRepository, OAuthRequest oAuthRequest, Gson gson) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.oAuthRequest = oAuthRequest;
        this.json = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LogRequest.logRequestInfo(request, logger);

        response.type("application/json");

        Optional<String> event = oAuthRequest.read(request.queryParams("eventUrl"));
        if(event.isPresent()) {
            logger.info("Read subscription ", event.get());
            SubscriptionEvent subscription = new SubsciptionReader(json).read(event.get());

            String accountId = createAcountFrom(subscription);
            logger.info("Created account: " + accountId);

            String userId = createUserAndAssingAccount(subscription, accountId);
            logger.info("Created user: " + userId);

            return json.toJson(succes(accountId));
        } else {
            return json.toJson(error("INTERNAL_ERROR", "Cannot read subscription"));
        }
    }

    private String createUserAndAssingAccount(SubscriptionEvent subscription, String accountId) {
        Creator creator = subscription.creator;
        User user = new User(creator.uuid, creator.firstName, creator.lastName, creator.email);
        user.accountId(accountId);
        return userRepository.create(user);
    }

    private String createAcountFrom(SubscriptionEvent subscription) {
        PricingModel pricingModel = fromValue(subscription.payload.order.editionCode);
        return accountRepository.create(pricingModel);
    }
}
