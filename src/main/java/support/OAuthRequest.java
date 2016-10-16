package support;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public OAuthRequest(String consumerKey, String consumerSecret, boolean signFetchEnabled) {
        this.consumerSecret = consumerSecret;
        this.consumerKey = consumerKey;
        this.signFetchEnabled = signFetchEnabled;
    }

    public Optional<String> read(String endpoint) {

        OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if(signFetchEnabled) {
                consumer.sign(connection);
            }
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            int responseCode = connection.getResponseCode();
            logger.info("sign request is " + responseCode);
            if (responseCode ==200) {
                String read = read(connection.getInputStream());
                logger.info("event:" + read);
                return of(read);
            }
        } catch (IOException | OAuthMessageSignerException | OAuthCommunicationException | OAuthExpectationFailedException e) {
            logger.error("Connection error", e);
        }
        return empty();
    }

    private String read(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(joining("\n"));
    }
}
