package app.http;

import spark.Request;
import spark.Response;
import spark.Route;

import static app.template.MustacheRenderer.renderTemplate;

public class ListMusicals implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return renderTemplate("views/list-musicals.mustache", this);
    }
}
