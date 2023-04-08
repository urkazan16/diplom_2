import site.nomoreparties.stellarburgers.constants.RandomTestUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.user.UserRegistrationFields;
import site.nomoreparties.stellarburgers.user.UserRequest;

import static site.nomoreparties.stellarburgers.constants.ResponseText.*;
import static org.hamcrest.Matchers.equalTo;

public class TestCaseRegistrationUser {

    String responseId;
    private UserRequest userRequest;
    private UserRegistrationFields userRegistrationFields;

    @Before
    public void setUp() {
        userRequest = new UserRequest();
        userRegistrationFields = RandomTestUser.getRandomRegistration();
    }

    @After
    public void clearUser() {
        if (responseId != null) {
            userRequest.deletingUser(responseId);
        }
    }

    @Test
    @DisplayName("Registration user")
    public void getTestRegistrationUser() {
        ValidatableResponse response = userRequest.registerUser(userRegistrationFields);
        responseId = response.extract().path(ACCESS_TOKEN);
        response.assertThat()
                .statusCode(200)
                .and()
                .body(SUCCESS, equalTo(true));
    }

    @Test
    @DisplayName("Registration user not fields email")
    public void testRegistrationUserNotFieldsEmail() {
        userRegistrationFields.setEmail(null);
        ValidatableResponse response = userRequest.registerUser(userRegistrationFields);
        responseId = response.extract().path(ACCESS_TOKEN);
        response.assertThat()
                .statusCode(403)
                .and()
                .body(MESSAGE, equalTo(REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Registration user not fields password")
    public void testRegistrationUserNotFieldsPassword() {
        userRegistrationFields.setPassword("");
        ValidatableResponse response = userRequest.registerUser(userRegistrationFields);
        responseId = response.extract().path(ACCESS_TOKEN);
        response.assertThat()
                .statusCode(403)
                .and()
                .body(MESSAGE, equalTo(REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Registration user not fields name")
    public void testRegistrationUserNotFieldsName() {
        userRegistrationFields.setName("");
        ValidatableResponse response = userRequest.registerUser(userRegistrationFields);
        responseId = response.extract().path(ACCESS_TOKEN);
        response.assertThat()
                .statusCode(403)
                .and()
                .body(MESSAGE, equalTo(REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Registration user repeat fields email")
    public void testRegistrationUserRepeatFields() {
        userRegistrationFields.setEmail(EMAIL);
        ValidatableResponse response = userRequest.registerUser(userRegistrationFields);
        userRequest.registerUser(userRegistrationFields);
        responseId = response.extract().path(ACCESS_TOKEN);
        response.assertThat()
                .statusCode(403)
                .and()
                .body(MESSAGE, equalTo(USER_ALREADY_EXISTS));
    }

}
