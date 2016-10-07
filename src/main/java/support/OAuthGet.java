package support;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class OAuthGet {

    private final String consumerSecret;
    private final String consumerKey;

    public OAuthGet(String consumerSecret, String consumerKey) {
        this.consumerSecret = consumerSecret;
        this.consumerKey = consumerKey;
    }

    public String execute(String endpoint) throws Exception {

        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("Accept", "application/json");
        OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
        consumer.sign(connection);

        connection.connect();
        if (connection.getResponseCode() >= 200 && connection.getResponseCode() < 400) {
            return read(connection.getInputStream());
        } else {
            throw new Exception("Request to " + url + " failed with status " + connection.getResponseCode());
        }
    }

    private String read(InputStream inputStream) throws IOException {
//        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(joining("\n"));
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }
}
