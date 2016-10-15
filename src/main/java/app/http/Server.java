package app.http;

import app.HelloApp;
import app.account.DatabaseAccountRepository;
import app.auth.MemoryAuthenticationService;
import app.controller.*;
import app.domain.Message;
import com.google.gson.Gson;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import static spark.Spark.*;

public class Server {

    private static Logger logger = LoggerFactory.getLogger(HelloApp.class);
    private final DataSource datasource;

    private Gson gson = new Gson();

    public Server(DataSource datasource) {
        this.datasource = datasource;
    }

    public void start(int port) {
        port(port);

        ConsumerManager consumerManager = new ConsumerManager();
        consumerManager.setAssociations(new InMemoryConsumerAssociationStore());
        consumerManager.setNonceVerifier(new InMemoryNonceVerifier(5000));


        staticFiles.location("/webapp");

        redirect.get("/", "/login");

//        List<String> publicPaths = new ArrayList<>();
//        publicPaths.add("/");
//        publicPaths.add("/login");
//        publicPaths.add("/api/subscription/create");
//        publicPaths.add("/api/subscription/cancel");

//        before("/api/*", new RequiresAuthenticationFilter(config, "FacebookClient"));

        get("/login", new LoginUser());
        get("/openid", new OpenIdLogin());
        post("/authenticate", new AuthenticatUser(new MemoryAuthenticationService()));
        get("/musicals", new ListMusicals());


        get("/subscription/create", new CreateSubscription(new DatabaseAccountRepository(datasource), gson));
        get("/subscription/change", new ChangeSubscription(datasource, gson));
        get("/subscription/cancel", new CancelSubscription(datasource, gson));
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
