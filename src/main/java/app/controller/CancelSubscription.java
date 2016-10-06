package app.controller;

import app.domain.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class CancelSubscription implements Route {

    private Logger logger = LoggerFactory.getLogger(CancelSubscription.class);

    @Override
    public Object handle(Request request, Response response) throws Exception {
        logger.info("Cancel + " + request.queryString());
        return new ApiResult("account-123", true);
    }
}
