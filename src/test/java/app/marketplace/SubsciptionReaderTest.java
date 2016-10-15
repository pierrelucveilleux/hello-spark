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
    public void canParseJson() throws Exception {
        SubscriptionEvent event = reader.read(someResponse());

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

    private String someResponse() {
        return ContentOf.resource("subscription/create.json");
    }
}