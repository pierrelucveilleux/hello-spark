package app.controller;

import app.auth.AuthenticationService;
import spark.Request;
import spark.Response;
import spark.Route;

public class AuthenticatUser implements Route {

    private final AuthenticationService authenticationService;

    public AuthenticatUser(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        String username = request.params("username");
        String password = request.params("password");


        return null;
    }
}
