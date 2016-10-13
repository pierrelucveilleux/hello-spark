package support;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.signature.QueryStringSigningStrategy;
import spark.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class OAuthRequest {

    private final String consumerSecret;
    private final String consumerKey;

    public OAuthRequest(String consumerSecret, String consumerKey) {
        this.consumerSecret = consumerSecret;
        this.consumerKey = consumerKey;
    }

    public String sign(Request request) throws Exception {

        OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
        consumer.setSigningStrategy(new QueryStringSigningStrategy());

        String endpoint = request.queryParams("eventUrl") + "?" + collectOauthParams(request);
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        consumer.sign(connection);
        connection.connect();

        if (connection.getResponseCode() >= 200 && connection.getResponseCode() < 400) {
            return read(connection.getInputStream());
        } else {
            throw new Exception("Request to " + url + " failed with status " + connection.getResponseCode());
        }
    }

    private String collectOauthParams(Request request) {
        return OAuth.OAUTH_CONSUMER_KEY + "=" + request.queryParamsValues(OAuth.OAUTH_CONSUMER_KEY)[0] +
            OAuth.OAUTH_NONCE + "=" + request.queryParamsValues(OAuth.OAUTH_NONCE)[0] +
            OAuth.OAUTH_TIMESTAMP + "=" + request.queryParamsValues(OAuth.OAUTH_TIMESTAMP)[0] +
            OAuth.OAUTH_VERSION + "=" + request.queryParamsValues(OAuth.OAUTH_VERSION)[0] +
            OAuth.OAUTH_SIGNATURE + "=" + request.queryParamsValues(OAuth.OAUTH_SIGNATURE)[0] +
            OAuth.OAUTH_SIGNATURE_METHOD + "=" + request.queryParamsValues(OAuth.OAUTH_SIGNATURE_METHOD)[0];
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
