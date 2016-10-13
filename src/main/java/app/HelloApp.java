package app;

import app.http.Server;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.database.DatabaseMigrator;

import javax.sql.DataSource;
import java.net.URISyntaxException;

import static java.lang.System.getenv;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static net.sourceforge.argparse4j.ArgumentParsers.newArgumentParser;

public class HelloApp {
    private static Logger logger = LoggerFactory.getLogger(HelloApp.class);

    public static void main(String[] args) throws URISyntaxException {

        System.out.println("Args = " + stream(args).collect(joining(", ")));

        ArgumentParser parser = newArgumentParser("HelloApp").defaultHelp(true);
        parser.addArgument("--dev").type(Boolean.class).setDefault(false).required(false);
        parser.addArgument("-p", "--port").type(Integer.class).setDefault(4567).required(false);

        Namespace namespace = parseArguments(parser, args);

        DataSource datasource = createDatasource(namespace.getBoolean("dev"));
        DatabaseMigrator databaseMigrator = new DatabaseMigrator(datasource);
        databaseMigrator.migrate();

        Server server = new Server(datasource);
        server.start(namespace.getInt("port"));
    }

    private static DataSource createDatasource(boolean modeDev) throws URISyntaxException {
        String dbUrl = modeDev ? "jdbc:postgresql://localhost/musicals" : getenv("JDBC_DATABASE_URL");
        String username = modeDev ? "root" : getenv("JDBC_DATABASE_USERNAME");
        String password = modeDev ? "root" :  getenv("JDBC_DATABASE_PASSWORD");

        HikariConfig configuration = new HikariConfig();
        configuration.setUsername(username);
        configuration.setPassword(password);
        configuration.setJdbcUrl(dbUrl);
        return new HikariDataSource(configuration);
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