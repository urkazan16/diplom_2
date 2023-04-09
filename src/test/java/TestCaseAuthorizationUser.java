import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.constants.RandomTestUser;
import site.nomoreparties.stellarburgers.user.UserAuthorizationFields;
import site.nomoreparties.stellarburgers.user.UserRegistrationFields;
import site.nomoreparties.stellarburgers.user.UserRequest;
import site.nomoreparties.stellarburgers.user.UserToken;

import static org.hamcrest.Matchers.equalTo;
import static site.nomoreparties.stellarburgers.constants.ResponseText.*;

public class TestCaseAuthorizationUser {

    private UserRequest userRequest;
    private UserRegistrationFields userRegistrationFields;
    private String token;

    private UserToken userTokenFields;

    @Before
    public void setUp() {
        userRequest = new UserRequest();
        userRegistrationFields = RandomTestUser.getRandomRegistration();
        token = userRequest.regUser(userRegistrationFields).path(ACCESS_TOKEN);
    }

    @After
    public void clearDate() {
        if (token != null && !token.isBlank()) {
            userRequest.deletingUser(token);
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
        userTokenFields = new UserToken(refreshToken);
        ValidatableResponse responseLogout = userRequest.logoutUser(userTokenFields);
        responseLogout.assertThat()
                .statusCode(200)
                .and()
                .body(MESSAGE, equalTo(SUCCESSFUL_LOGOUT));

    }
}


