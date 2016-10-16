package app.controller;

import app.assignment.UserAssignment;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;

public class UserAssignementMatcher {
    public static Matcher<UserAssignment> assignment(String userId, String accountId) {
        return allOf(withUserId(userId), withAccountId(accountId));
    }

    private static Matcher<UserAssignment> withUserId(String userId) {
        return new FeatureMatcher<UserAssignment, String>(equalTo(userId), "a user with id", "userId") {
            @Override
            protected String featureValueOf(UserAssignment assignment) {
                return assignment.userId();
            }
        };
    }

    private static Matcher<UserAssignment> withAccountId(String accountId) {
        return new FeatureMatcher<UserAssignment, String>(equalTo(accountId), "an account with id", "accountId") {
            @Override
            protected String featureValueOf(UserAssignment assignment) {
                return assignment.accountId();
            }
        };
    }
}
