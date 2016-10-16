package app.marketplace;

import com.google.gson.Gson;

public class EventReader<T> {
    private final Gson gson;

    public EventReader(Gson gson) {
        this.gson = gson;
    }

    public T read(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
