package app.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.sql.DataSource;

import static app.domain.ApiResult.succes;
import static java.util.Arrays.asList;

public class ChangeSubscription implements Route {

    private Logger logger = LoggerFactory.getLogger(CancelSubscription.class);

    private final DataSource datasource;
    private final Gson gson;

    public ChangeSubscription(DataSource datasource, Gson gson) {
        this.datasource = datasource;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        logger.info("Change + " + request);

        request.queryParams().forEach((k) -> logger.info("Item : " + k + " Value: " + asList(request.queryParamsValues(k))));

        response.type("application/json");
        return gson.toJson(succes("account-123"));
    }
}
