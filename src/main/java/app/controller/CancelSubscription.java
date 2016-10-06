package app.controller;

import app.domain.ApiResult;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class CancelSubscription implements Route {

    private Logger logger = LoggerFactory.getLogger(CancelSubscription.class);

    private final Gson gson;

    public CancelSubscription(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        logger.info("Cancel + " + request);

        response.type("application/json");
        return gson.toJson(new ApiResult("account-123", true));
    }
}
