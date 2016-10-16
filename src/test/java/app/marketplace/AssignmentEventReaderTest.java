package app.marketplace;

import app.marketplace.assignment.AssignmentEvent;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static support.ContentOf.resource;

public class AssignmentEventReaderTest {

    EventReader<AssignmentEvent> reader = new EventReader<>(new Gson());

    @Test
    public void canReadAssignment() throws Exception {
        AssignmentEvent event = reader.read(resource("assignment/assignment.json"), AssignmentEvent.class);

        assertThat(event, not(nullValue()));
        assertThat(event.type, equalTo("USER_ASSIGNMENT"));

        assertThat(event.creator, not(nullValue()));
        assertThat(event.creator.uuid, equalTo("7ac30510-c54c-45ca-9c2f-f4d6b3aa2c15"));
        assertThat(event.creator.openId, equalTo("https://www.acme.com/openid/id/7ac30510-c54c-45ca-9c2f-f4d6b3aa2c15"));
        assertThat(event.creator.email, equalTo("test-email+creator@appdirect.com"));
        assertThat(event.creator.firstName, equalTo("Another"));
        assertThat(event.creator.lastName, equalTo("User"));

        assertThat(event.marketplace, not(nullValue()));
        assertThat(event.marketplace.partner, equalTo("APPDIRECT"));
        assertThat(event.marketplace.baseUrl, equalTo("https://www.acme.com"));

        assertThat(event.payload, not(nullValue()));
        assertThat(event.payload.user, not(nullValue()));
        assertThat(event.payload.user.uuid, equalTo("7ac30510-c54c-45ca-9c2f-f4d6b3aa2c15"));
        assertThat(event.payload.user.openId, equalTo("https://www.acme.com/openid/id/7ac30510-c54c-45ca-9c2f-f4d6b3aa2c15"));
        assertThat(event.payload.user.firstName, equalTo("Another"));
        assertThat(event.payload.user.lastName, equalTo("User"));
        assertThat(event.payload.user.email, equalTo("test-email+user@appdirect.com"));

        assertThat(event.marketplace.baseUrl, equalTo("https://www.acme.com"));
    }
}