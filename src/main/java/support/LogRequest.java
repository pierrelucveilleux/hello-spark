package support;

import org.slf4j.Logger;
import spark.Request;

import static java.util.Arrays.asList;

public class LogRequest {
    public static void logRequestInfo(Request request, Logger logger) {
        logger.info("Create: " + request.uri() + ",  " + request.body());
        request.headers().forEach((h) -> logger.info("Header : " + h + " Value: " + request.headers(h)));
        request.queryParams().forEach((k) -> logger.info("Param: " + k + " Value: " + asList(request.queryParamsValues(k))));
    }
}
