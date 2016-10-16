package app.controller;

import app.assignment.UserAssignment;
import app.assignment.UserAssignmentRepository;
import app.marketplace.EventReader;
import app.marketplace.assignment.AssignmentEvent;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import support.LogRequest;
import support.OAuthRequest;

import java.util.Optional;

import static app.domain.ApiResult.error;
import static app.domain.ApiResult.succesUser;

public class AssignUser implements Route {

    private static Logger logger = LoggerFactory.getLogger(AssignUser.class);

    private final UserAssignmentRepository userAssignmentRepository;
    private final OAuthRequest signRequest;
    private final Gson gson;

    public AssignUser(UserAssignmentRepository userAssignmentRepository, OAuthRequest signRequest, Gson gson) {
        this.userAssignmentRepository = userAssignmentRepository;
        this.signRequest = signRequest;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LogRequest.logRequestInfo(request, logger);

        response.type("application/json");

        Optional<String> body = signRequest.read(request.queryParams("eventUrl"));
        if(body.isPresent()) {
            logger.info("Read event", body.get());

            AssignmentEvent event = new EventReader<AssignmentEvent>(gson).read(body.get(), AssignmentEvent.class);

            String accountIdentifier = event.payload.account.accountIdentifier;
            String uuid = event.payload.user.uuid;

            userAssignmentRepository.assign(new UserAssignment(uuid, accountIdentifier));

            logger.info("User assigned : " + uuid +  " to account " + body);
            return gson.toJson(succesUser());
        } else {
            return gson.toJson(error("INTERNAL_ERROR", "Cannot read subscription"));
        }
    }
}
