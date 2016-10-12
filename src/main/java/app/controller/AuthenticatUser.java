package app.controller;

import app.auth.AuthenticationService;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.halt;

public class AuthenticatUser implements Route {

    private final AuthenticationService authenticationService;

    public AuthenticatUser(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        String email = request.queryParams("email");
        String password = request.queryParams("password");

        boolean authenticate = authenticationService.authenticate(email, password);
        if(!authenticate) {
            halt(401, "Cannot login user with current credentials");
        }
        response.redirect("/musicals");
        return null;
    }
}
