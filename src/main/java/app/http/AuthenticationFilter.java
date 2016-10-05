package app.http;

import app.auth.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.halt;

public class AuthenticationFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final AuthenticationService authenticationService;
    private final List<String> publicPaths;

    public AuthenticationFilter(AuthenticationService authenticationService, List<String> publicPaths) {
        this.authenticationService = authenticationService;
        this.publicPaths = publicPaths;
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        if(publicPaths.contains(request.pathInfo())) {
            logger.info("Path is public: " +  request.pathInfo());
            return;
        }

        boolean authenticated = request.session().attribute("user") != null;
        if (!authenticated) {
            halt(401, "You are not welcome here");
        }

    }
}
