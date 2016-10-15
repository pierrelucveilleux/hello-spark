package app.controller;

import app.account.Account;
import app.account.AccountRepository;
import app.domain.ApiResult;
import app.marketplace.SubsciptionReader;
import app.marketplace.SubscriptionEvent;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import support.OAuthRequest;

import java.util.Optional;

import static java.util.Arrays.asList;

public class CreateSubscription implements Route {

    private Logger logger = LoggerFactory.getLogger(CreateSubscription.class);

    private final AccountRepository accountRepository;
    private final Gson gson;

    public CreateSubscription(AccountRepository accountRepository, Gson gson) {
        this.accountRepository = accountRepository;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        response.type("application/json");

        logger.info("Create: " + request.uri() + ",  " + request.body());
        request.headers().forEach((h) -> logger.info("Header : " + h + " Value: " + request.headers(h)));
        request.queryParams().forEach((k) -> logger.info("Item : " + k + " Value: " + asList(request.queryParamsValues(k))));

        OAuthRequest oAuth = new OAuthRequest("job-138569", "sxYTtH7x1Du0Y");

        Optional<String> body = oAuth.sign(request);
        if(body.isPresent()) {
            SubsciptionReader subsciptionReader = new SubsciptionReader(gson);
            SubscriptionEvent subscriptionEvent = subsciptionReader.read(body.get());

            String accountId = accountRepository.create(Account.PricingModel.Free);
            logger.info("Created account: " + accountId +  ", Body: " + body);
            return gson.toJson(new ApiResult(accountId, true));
        } else {
            return gson.toJson(new ApiResult("ERROR", false));
        }
    }
}
