package app.marketplace;

import app.marketplace.subscription.SubsciptionReader;
import app.marketplace.subscription.SubscriptionEvent;
import com.google.gson.Gson;
import org.junit.Test;
import support.ContentOf;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class SubsciptionReaderTest {

    SubsciptionReader reader = new SubsciptionReader(new Gson());

    @Test
    public void canParseCreateSubscription() throws Exception {
        SubscriptionEvent event = reader.read(someResponse("subscription/create.json"));

        assertThat(event, not(nullValue()));
        assertThat(event.type, equalTo("SUBSCRIPTION_ORDER"));
        assertThat(event.returnUrl, equalTo("https://www.appdirect.com/finishprocure?token=dummyOrder"));

        assertThat(event.creator, not(nullValue()));
        assertThat(event.creator.uuid, equalTo("ec5d8eda-5cec-444d-9e30-125b6e4b67e2"));
        assertThat(event.creator.openId, equalTo("https://www.appdirect.com/openid/id/ec5d8eda-5cec-444d-9e30-125b6e4b67e2"));
        assertThat(event.creator.email, equalTo("test-email+creator@appdirect.com"));
        assertThat(event.creator.firstName, equalTo("DummyCreatorFirst"));
        assertThat(event.creator.lastName, equalTo("DummyCreatorLast"));

        assertThat(event.marketplace, not(nullValue()));
        assertThat(event.marketplace.partner, equalTo("ACME"));
        assertThat(event.marketplace.baseUrl, equalTo("https://acme.appdirect.com"));

        assertThat(event.payload, not(nullValue()));
        assertThat(event.payload.company, not(nullValue()));
        assertThat(event.payload.company.name, equalTo("Example Company Name"));

        assertThat(event.payload.order, not(nullValue()));
        assertThat(event.payload.order.editionCode, equalTo("FREE"));
        assertThat(event.payload.order.editionCode, equalTo("FREE"));
        assertThat(event.payload.order.pricingModel, equalTo("MONTHLY"));
    }

    @Test
    public void canParseCancelSubscription() throws Exception {
        SubscriptionEvent event = reader.read(someResponse("subscription/cancel.json"));

        assertThat(event, not(nullValue()));
        assertThat(event.type, equalTo("SUBSCRIPTION_CANCEL"));

        assertThat(event.creator, not(nullValue()));
        assertThat(event.creator.uuid, equalTo("29da8333-e20a-4200-9335-78a884cb468a"));
        assertThat(event.creator.openId, equalTo("https://marketplace.appdirect.com/openid/id/29da8333-e20a-4200-9335-78a884cb468a"));
        assertThat(event.creator.email, equalTo("pierrelucveilleux@gmail.com"));
        assertThat(event.creator.firstName, equalTo("Pierre-Luc"));
        assertThat(event.creator.lastName, equalTo("Veilleux"));

        assertThat(event.marketplace, not(nullValue()));
        assertThat(event.marketplace.partner, equalTo("APPDIRECT"));
        assertThat(event.marketplace.baseUrl, equalTo("https://marketplace.appdirect.com"));

        assertThat(event.payload, not(nullValue()));
        assertThat(event.payload.company, nullValue());

        assertThat(event.payload.order, nullValue());

        assertThat(event.payload.account, not(nullValue()));
        assertThat(event.payload.account.accountIdentifier, equalTo("7aa211cc-f0d4-457c-b53b-0d3f13d5a24b"));
    }

    private String someResponse(String name) {
        return ContentOf.resource(name);
    }
}