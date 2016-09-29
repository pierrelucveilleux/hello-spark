package app;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import spark.Redirect;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.redirect;

public class HelloApp {
    public static void main(String[] args) {
        System.out.println("Args = " + stream(args).collect(joining(", ")));

        ArgumentParser parser = ArgumentParsers.newArgumentParser("HelloApp")
                .defaultHelp(true)
                .description("Test SparkJava framework.");
        parser.addArgument("-p", "--port").type(Integer.class).setDefault(4567).required(false);

        Namespace namespace = parseArguments(parser, args);

        port(namespace.getInt("port"));

        redirect.get("/", "/hello");
        
        get("/hello", (request, response) -> "Hello World");
        get("/hello/:name", (request, response) -> "Hello: " + request.params(":name"));
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