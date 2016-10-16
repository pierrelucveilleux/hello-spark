package app.controller;

import app.assignment.UserAssignmentRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import support.OAuthRequest;

public class UnassignUser implements Route {

    private static Logger logger = LoggerFactory.getLogger(UnassignUser.class);

    private final UserAssignmentRepository userAssignmentRepository;
    private final OAuthRequest signRequest;
    private final Gson gson;

    public UnassignUser(UserAssignmentRepository userAssignmentRepository, OAuthRequest signRequest, Gson gson) {
        this.userAssignmentRepository = userAssignmentRepository;
        this.signRequest = signRequest;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
