package app;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static spark.Spark.get;

public class HelloApp {
    public static void main(String[] args) {
        System.out.println("Args = " + stream(args).collect(joining(", ")));
        get("/hello", (request, response) -> "Hello World");
        get("/hello/:name",
                (request, response) -> "Hello: " + request.params(":name")
        );
    }
}