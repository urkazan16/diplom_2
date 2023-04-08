import site.nomoreparties.stellarburgers.constants.RandomTestUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.user.UserAuthorizationFields;
import site.nomoreparties.stellarburgers.user.UserRegistrationFields;
import site.nomoreparties.stellarburgers.user.UserRequest;
import site.nomoreparties.stellarburgers.user.UserTokenFields;

import static site.nomoreparties.stellarburgers.constants.ResponseText.*;
import static org.hamcrest.Matchers.equalTo;

public class TestCaseAuthorizationUser {

    private UserRequest userRequest;
    private UserRegistrationFields userRegistrationFields;
    private String responseId;

    private UserTokenFields userTokenFields;

    @Before
    public void setUp() {
        userRequest = new UserRequest();
        userRegistrationFields = RandomTestUser.getRandomRegistration();
        responseId = userRequest.regUser(userRegistrationFields).path(ACCESS_TOKEN);
    }

    @After
    public void clearDate() {
        if (responseId != null) {
            userRequest.deletingUser(responseId);
        }
    }

    @Test
    @DisplayName("Authorization user")
    public void testAuthorizationUser() {
        ValidatableResponse response = userRequest.authorizationUser(UserAuthorizationFields.from(userRegistrationFields));
        response.assertThat()
                .statusCode(200)
                .and()
                .body(SUCCESS, equalTo(true));
    }

    @Test
    @DisplayName("Authorization not fields user password")
    public void testAuthorizationNotUserFieldsPassword() {
        userRegistrationFields.setPassword(USER_PASSWORD);
        ValidatableResponse response = userRequest.authorizationUser(UserAuthorizationFields.from(userRegistrationFields));
        response.assertThat()
                .statusCode(401)
                .and()
                .body(MESSAGE, equalTo(INCORRECT));
    }

    @Test
    @DisplayName("Authorization not fields user email")
    public void testAuthorizationNotUserFieldsEmail() {
        userRegistrationFields.setEmail(EMAIL);
        ValidatableResponse response = userRequest.authorizationUser(UserAuthorizationFields.from(userRegistrationFields));
        response.assertThat()
                .statusCode(401)
                .and()
                .body(MESSAGE, equalTo(INCORRECT));
    }

    @Test
    @DisplayName("Logout authorization user")
    public void testLogoutAuthorizationUser() {
        ValidatableResponse response = userRequest.authorizationUser(UserAuthorizationFields.from(userRegistrationFields));
        String refreshToken = response.extract().path(REFRESH_TOKEN);
        userTokenFields = new UserTokenFields(refreshToken);
        ValidatableResponse responseLogout = userRequest.logoutUser(userTokenFields);
        responseLogout.assertThat()
                .statusCode(200)
                .and()
                .body(MESSAGE, equalTo(SUCCESSFUL_LOGOUT));

    }
}


