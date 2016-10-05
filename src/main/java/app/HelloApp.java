package app;

import app.http.Server;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class HelloApp {
    private static Logger logger = LoggerFactory.getLogger(HelloApp.class);

    public static void main(String[] args) {

        System.out.println("Args = " + stream(args).collect(joining(", ")));

        ArgumentParser parser = ArgumentParsers.newArgumentParser("HelloApp")
                .defaultHelp(true)
                .description("Test SparkJava framework.");
        parser.addArgument("-p", "--port").type(Integer.class).setDefault(4567).required(false);

        Namespace namespace = parseArguments(parser, args);

        Server server = new Server();
        server.start(namespace.getInt("port"));
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