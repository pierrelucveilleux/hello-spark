package app.controller;

import app.domain.BinResponse;
import app.domain.Message;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import spark.Request;
import spark.Response;
import spark.Route;

public class HttpBinGet implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {

        Unirest.setObjectMapper(new ObjectMapper() {
            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                return new GsonBuilder().create().fromJson(value, valueType);
            }

            @Override
            public String writeValue(Object value) {
                return new GsonBuilder().create().toJson(value);
            }
        });

        HttpResponse<BinResponse> apiResponse = Unirest.get("http://httpbin.org/{method}")
                .routeParam("method", "get")
                .queryString("name", "Mark")
                .asObject(BinResponse.class);
        return new GsonBuilder().create().toJson(new Message("GET", apiResponse.getBody().origin));
    }
}
