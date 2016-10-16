package app.http.openid;

import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ax.FetchRequest;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.openid.client.BaseOpenIdClient;

public class AppDirectOpenIdClient extends BaseOpenIdClient<AppDirectProfile> {
    @Override
    protected String getUser(WebContext context) {
        return context.getRequestParameter("openIdUser");
    }

    @Override
    protected FetchRequest getFetchRequest() throws MessageException {
        FetchRequest fetchRequest = FetchRequest.createFetchRequest();
        fetchRequest.addAttribute("email", "http://axschema.org/contact/email", true);
        fetchRequest.addAttribute("fullname", "http://axschema.org/namePerson", true);
        fetchRequest.addAttribute("uuid", "https://www.appdirect.com/schema/user/uuid", true);
        return fetchRequest;
    }

    @Override
    protected AppDirectProfile createProfile(AuthSuccess authSuccess) throws HttpAction, MessageException {
        return new AppDirectProfile();
    }
}
