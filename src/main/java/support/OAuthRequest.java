package support;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.joining;


public class OAuthRequest {

    private static Logger logger = LoggerFactory.getLogger(OAuthRequest.class);

    private final String consumerSecret;
    private final String consumerKey;
    private boolean signFetchEnabled;

    public OAuthRequest(String consumerSecret, String consumerKey, boolean signFetchEnabled) {
        this.consumerSecret = consumerSecret;
        this.consumerKey = consumerKey;
        this.signFetchEnabled = signFetchEnabled;
    }

    public Optional<String> sign(Request request) {

        OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
        String endpoint = request.queryParams("eventUrl");
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if(signFetchEnabled) {
                consumer.sign(connection);
            }
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                String read = read(connection.getInputStream());
                connection.disconnect();
                return of(read);
            }
        } catch (IOException | OAuthMessageSignerException | OAuthCommunicationException | OAuthExpectationFailedException e) {
            logger.error("", e);
        }
        return empty();
    }

    private String read(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(joining("\n"));
    }
}
