package app.http.openid;

import org.pac4j.core.authorization.authorizer.IsAuthenticatedAuthorizer;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.core.matching.ExcludedPathMatcher;
import org.pac4j.sparkjava.DefaultHttpActionAdapter;

public class OpenIdConfigFactory implements ConfigFactory {

    @Override
    public Config build() {
        Client appDirectClient = new AppDirectOpenIdClient();
        Clients clients = new Clients("https://pluc-says-hello-spark.herokuapp.com/callback", appDirectClient);
        Config config = new Config(clients);
        config.addAuthorizer("custom", new IsAuthenticatedAuthorizer());
        config.addMatcher("excludedPath", new ExcludedPathMatcher("^/openid$"));
        config.setHttpActionAdapter(new DefaultHttpActionAdapter());
        return config;
    }
}
