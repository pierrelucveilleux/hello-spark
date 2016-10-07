package app.market;

import com.google.gson.Gson;

public class SubsciptionReader {

    private final Gson gson;

    public SubsciptionReader(Gson gson) {
        this.gson = gson;
    }

    public SubscriptionEvent read(String json) {
        return gson.fromJson(json, SubscriptionEvent.class);
    }
}
