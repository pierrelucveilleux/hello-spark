package app.controller;

import spark.Request;
import spark.Response;
import spark.Route;

import static app.template.MustacheRenderer.renderTemplate;

public class LoginUser implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return renderTemplate("views/login.mustache", this);
    }
}
