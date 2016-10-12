package app.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class OpenIdLogin implements Route {

    private static Logger logger = LoggerFactory.getLogger(OpenIdLogin.class);

    @Override
    public Object handle(Request request, Response response) throws Exception {


        String openId = request.queryParams("openId");
        String accountId = request.queryParams("accountId");

        logger.info("OpenId: " + openId + ", account:" + accountId);
        return null;
    }
}
