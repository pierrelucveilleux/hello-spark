package support;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.util.stream.Collectors.joining;


public class OAuthGet {

    private final String consumerSecret;
    private final String consumerKey;

    public OAuthGet(String consumerSecret, String consumerKey) {
        this.consumerSecret = consumerSecret;
        this.consumerKey = consumerKey;
    }

    public String execute(String endpoint) throws Exception {

        OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);

        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        consumer.sign(connection);
        connection.addRequestProperty("Accept", "application/json");

        connection.connect();

        if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 400) {
            return read(connection.getInputStream());
        } else {
            throw new Exception("Request to " + url + " failed with status " + connection.getResponseCode());
        }
    }

    private String read(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(joining("\n"));
    }
}
