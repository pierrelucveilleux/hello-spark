package app;

import static spark.Spark.get;

public class HelloApp {
    public static void main(String[] args) {
        get("/hello", (request, response) -> "Hello World");
        get("/hello/:name",
                (request, response) -> "Hello: " + request.params(":name")
        );
    }
}