package app.market;

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
        assertThat(event.payload, not(nullValue()));
        assertThat(event.payload.company, not(nullValue()));
        assertThat(event.payload.company.name, equalTo("Example Company Name"));
    }

    private String someResponse() {
        return ContentOf.resource("subscription/create.json");
    }
}