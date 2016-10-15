package app.controller;

import app.account.Account;
import app.account.AccountRepository;
import app.domain.ApiResult;
import app.marketplace.SubsciptionReader;
import app.marketplace.SubscriptionEvent;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuthService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

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

//        OAuthRequest oAuth = new OAuthRequest("job-138569", "BOVfHqHi5jsQ", false);
        OAuthService service = new OAuth10aService(null, new OAuthConfig("job-138569", "xYTtH7x1Du0Y"));
        com.github.scribejava.core.model.OAuthRequest oAuth = new com.github.scribejava.core.model.OAuthRequest(Verb.GET, "https://marketplace.appdirect.com/api/integration/v1/events/dummyOrder", service);
        com.github.scribejava.core.model.Response oAuthResp = oAuth.send();
        Optional<String> body = Optional.of(oAuthResp.getBody());

//        Optional<String> body = oAuth.sign(request);
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
