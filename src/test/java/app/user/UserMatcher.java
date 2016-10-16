package app.user;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;

public class UserMatcher {

    public static Matcher<User> user(User user) {
        return allOf(withUuid(user.uuid()), withOpenId(user.openid()),
                withFirstName(user.firstName()), withLastName(user.lastName()),
                withEmail(user.email()), withAccountId(user.accountId()));
    }

    private static Matcher<User> withUuid(String uuid) {
        return new FeatureMatcher<User, String>(equalTo(uuid), "a user with uuid", "uuid") {
            @Override
            protected String featureValueOf(User user) {
                return user.uuid();
            }
        };
    }

    private static Matcher<User> withOpenId(String openId) {
        return new FeatureMatcher<User, String>(equalTo(openId), "a user with openId", "openId") {
            @Override
            protected String featureValueOf(User user) {
                return user.openid();
            }
        };
    }

    private static Matcher<User> withFirstName(String firstName) {
        return new FeatureMatcher<User, String>(equalTo(firstName), "a user with firstName", "firstName") {
            @Override
            protected String featureValueOf(User user) {
                return user.firstName();
            }
        };
    }

    private static Matcher<User> withLastName(String lastName) {
        return new FeatureMatcher<User, String>(equalTo(lastName), "a user with lastName", "lastName") {
            @Override
            protected String featureValueOf(User user) {
                return user.lastName();
            }
        };
    }

    private static Matcher<User> withEmail(String email) {
        return new FeatureMatcher<User, String>(equalTo(email), "a user with email", "email") {
            @Override
            protected String featureValueOf(User user) {
                return user.email();
            }
        };
    }

    private static Matcher<User> withAccountId(String accountId) {
        return new FeatureMatcher<User, String>(equalTo(accountId), "a user with accountId", "accountId") {
            @Override
            protected String featureValueOf(User user) {
                return user.accountId();
            }
        };
    }
}
