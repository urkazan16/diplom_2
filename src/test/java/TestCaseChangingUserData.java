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

public class TestCaseChangingUserData {

    private UserRequest userRequest;
    private UserRegistrationFields userRegistrationFields;
    private String responseId;

    @Before
    public void setUp() {
        userRequest = new UserRequest();
        userRegistrationFields = RandomTestUser.getRandomRegistration();
        ValidatableResponse response = userRequest.registerUser(userRegistrationFields);
        responseId = response.extract().path(ACCESS_TOKEN);
    }

    @After
    public void clearDate() {
        if (responseId != "") {
            userRequest.deletingUser(responseId);
        }
    }

    @Test
    @DisplayName("Changing user email data")
    public void testChangingAuthUserEmail() {
        userRegistrationFields.setEmail(userRegistrationFields.getEmail());
        ValidatableResponse response = userRequest.changingUser(userRegistrationFields, responseId);
        response.assertThat()
                .statusCode(200)
                .and()
                .body(USER_EMAIL, equalTo(userRegistrationFields.getEmail()));
    }

    @Test
    @DisplayName("Changing user name data")
    public void testChangingAuthUserName() {
        userRegistrationFields.setName(userRegistrationFields.getName());
        ValidatableResponse response = userRequest.changingUser(userRegistrationFields, responseId);
        response.assertThat()
                .statusCode(200)
                .and()
                .body(USER_NAME, equalTo(userRegistrationFields.getName()));
    }

    @Test
    @DisplayName("Changing user password data")
    public void testChangingAuthUserPassword() {
        userRegistrationFields.setPassword(userRegistrationFields.getPassword());
        ValidatableResponse response = userRequest.changingUser(userRegistrationFields, responseId);
        response.assertThat()
                .statusCode(200)
                .and()
                .body(SUCCESS, equalTo(true));
    }

    @Test
    @DisplayName("Changing user data without authorization no token")
    public void testChangingNoAutNotTokenUserName() {
        userRegistrationFields.setName(userRegistrationFields.getName());
        ValidatableResponse response = userRequest.changingUser(userRegistrationFields, "");
        response.assertThat()
                .statusCode(401)
                .and()
                .body(MESSAGE, equalTo(NOT_AUTHORISED));
    }

}
