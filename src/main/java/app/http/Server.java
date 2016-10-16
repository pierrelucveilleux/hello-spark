package app.http;

import app.HelloApp;
import app.account.AccountRepository;
import app.account.DatabaseAccountRepository;
import app.assignment.DatabaseUserAssignmentRepository;
import app.assignment.UserAssignmentRepository;
import app.controller.*;
import app.http.openid.OpenIdConfigFactory;
import app.user.DatabaseUserRepository;
import app.user.UserRepository;
import com.google.gson.Gson;
import org.pac4j.core.config.Config;
import org.pac4j.sparkjava.CallbackRoute;
import org.pac4j.sparkjava.SecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.OAuthRequest;

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

        staticFiles.location("/webapp");

        AccountRepository accountRepository = new DatabaseAccountRepository(datasource);
        UserRepository userRepository = new DatabaseUserRepository(datasource);
        UserAssignmentRepository userAssignmentRepository = new DatabaseUserAssignmentRepository(datasource);

        OAuthRequest signRequest = new OAuthRequest("job-138569", "xYTtH7x1Du0Y", true);

        Config config = new OpenIdConfigFactory().build();
        CallbackRoute callback = new CallbackRoute(config, null, true);
        get("/callback", callback);
        post("/callback", callback);

        SecurityFilter userMustBeAuthentified = new SecurityFilter(config, "AppDirectOpenIdClient", "", "excludedPath");
        get("/openid", new OpenIdLogin());
//        post("/authenticate", new AuthenticatUser(new MemoryAuthenticationService()));

        before("/musicals", userMustBeAuthentified);
        get("/musicals", new ListMusicals());
        redirect.get("/", "/musicals");

        get("/subscription/create", new CreateSubscription(accountRepository, userRepository, userAssignmentRepository, signRequest, gson));
        get("/subscription/change", new ChangeSubscription(accountRepository, signRequest, gson));
        get("/subscription/cancel", new CancelSubscription(accountRepository, userAssignmentRepository, signRequest, gson));

        get("/user/assignment", new AssignUser(userAssignmentRepository, signRequest, gson));
        get("/user/unassignment", new UnassignUser(userAssignmentRepository, signRequest, gson));

        exception(Exception.class, (exception, request, response) -> {
            logger.error("An error occured !!", exception);
            response.status(503);
            response.body("Server error");
        });
    }
}
