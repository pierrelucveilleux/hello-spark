package app.controller;

import app.domain.ApiResult;
import app.market.SubsciptionReader;
import app.market.SubscriptionEvent;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import support.OAuthGet;

import static java.util.Arrays.asList;

public class CreateSubscription implements Route {

    private Logger logger = LoggerFactory.getLogger(CreateSubscription.class);

    private final SubsciptionReader subsciptionReader;
    private final Gson gson;

    public CreateSubscription(Gson gson) {
        this.gson = gson;
        this.subsciptionReader = new SubsciptionReader(gson);
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        logger.info("Create: " + request.uri() + ",  " + request.body());
        request.headers().forEach((h) -> logger.info("Header : " + h + " Value: " + request.headers(h)));
        request.queryParams().forEach((k) -> logger.info("Item : " + k + " Value: " + asList(request.queryParamsValues(k))));

        String eventUrl = request.queryParams("eventUrl");
        OAuthGet oAuthGet = new OAuthGet("job-138569", "xYTtH7x1Du0Y");

        String body = oAuthGet.execute(eventUrl);
        SubscriptionEvent subscriptionEvent = subsciptionReader.read(body);
        logger.info("Body: " + body);

        response.type("application/json");
        return gson.toJson(new ApiResult("account-123", true));
    }
}
