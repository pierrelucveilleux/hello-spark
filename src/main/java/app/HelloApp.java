package app;

import app.controller.HttpBinGet;
import app.domain.Message;
import com.google.gson.Gson;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static spark.Spark.*;

public class HelloApp {
    private static Logger logger = LoggerFactory.getLogger(HelloApp.class);

    public static void main(String[] args) {

        System.out.println("Args = " + stream(args).collect(joining(", ")));

        ArgumentParser parser = ArgumentParsers.newArgumentParser("HelloApp")
                .defaultHelp(true)
                .description("Test SparkJava framework.");
        parser.addArgument("-p", "--port").type(Integer.class).setDefault(4567).required(false);

        Namespace namespace = parseArguments(parser, args);

        port(namespace.getInt("port"));

        redirect.get("/", "/hello");

        Gson gson = new Gson();
        get("/hello", (request, response) -> "Hello World");
        get("/rest", new HttpBinGet());
        get("/hello/:name", (request, response) -> "Hello: " + request.params(":name"));
        get("/json/hello/:name", "application/json", (request, response) -> new Message("Hello, ", request.params("name")), gson::toJson);
        get("/json/hello/:name", "application/json", (request, response) -> new Message("Hello, ", request.params("name")), gson::toJson);
        exception(Exception.class, (exception, request, response) -> {
            logger.error("An error occured !!", exception);
            response.status(503);
            response.body("Server error");
        });
    }

    private static Namespace parseArguments(ArgumentParser parser, String[] args) {
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        return ns;
    }
}