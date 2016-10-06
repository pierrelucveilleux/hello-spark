package app.http;

import app.controller.CancelSubscription;
import app.controller.CreateSubscription;
import app.HelloApp;
import app.auth.AuthenticationService;
import app.auth.MemoryAuthenticationService;
import app.controller.AuthenticatUser;
import app.controller.LoginUser;
import app.domain.Message;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class Server {

    private static Logger logger = LoggerFactory.getLogger(HelloApp.class);

    private Gson gson = new Gson();

    public void start(int port) {
        port(port);

        redirect.get("/", "/login");

        List<String> publicPaths = new ArrayList<>();
        publicPaths.add("/");
        publicPaths.add("/login");
        publicPaths.add("/subscription/create");
        publicPaths.add("/subscription/cancel");

        AuthenticationService authenticationService = new MemoryAuthenticationService();

        before(new AuthenticationFilter(authenticationService, publicPaths));

        get("/login", new LoginUser());
        post("/authenticate", new AuthenticatUser(authenticationService));

        get("/subscription/create", new CreateSubscription(), gson::toJson);
        get("/subscription/cancel", new CancelSubscription(), gson::toJson);
        get("/hello/:name", (request, response) -> "Hello: " + request.params(":name"));
        get("/json/hello/:name", "application/json", (request, response) -> new Message("Hello, ", request.params("name")), gson::toJson);
        get("/json/hello/:name", "application/json", (request, response) -> new Message("Hello, ", request.params("name")), gson::toJson);
        exception(Exception.class, (exception, request, response) -> {
            logger.error("An error occured !!", exception);
            response.status(503);
            response.body("Server error");
        });

    }
}
