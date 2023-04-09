package site.nomoreparties.stellarburgers.user;

import site.nomoreparties.stellarburgers.constants.request.Header;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class UserRequest extends Header {
    private static final String USER_URL = BASE_URL + "auth/";

    @Step("Create user {userRegistrationFields}")
    public ValidatableResponse registerUser(UserRegistrationFields userRegistrationFields) {
        return given()
                .spec(getRequestSpec())
                .body(userRegistrationFields)
                .post(USER_URL + "register/").then();
    }

    @Step("Create user {userRegistrationFields}")
    public Response regUser(UserRegistrationFields userRegistrationFields) {
        return given()
                .spec(getRequestSpec())
                .body(userRegistrationFields)
                .post(USER_URL + "register/");
    }


    @Step("Authorization User {userAuthorizationFields}")
    public ValidatableResponse authorizationUser(UserAuthorizationFields userAuthorizationFields) {
        return given()
                .spec(getRequestSpec())
                .body(userAuthorizationFields)
                .post(USER_URL + "login/").then();
    }

    @Step("Changing User {userRegistrationFields}")
    public ValidatableResponse changingUser(UserRegistrationFields userRegistrationFields, String requestBody) {
        return given()
                .spec(getRequestAuthSpec(requestBody))
                .body(userRegistrationFields)
                .patch(USER_URL + "user/").then();
    }

    @Step("Deleting User {requestBody}")
    public ValidatableResponse deletingUser(String requestBody) {
        return given()
                .spec(getRequestAuthSpec(requestBody))
                .delete(USER_URL + "user/").then();
    }

    @Step("Deleting User {userTokenFields}")
    public ValidatableResponse logoutUser(UserToken userTokenFields) {
        return given()
                .spec(getRequestSpec())
                .body(userTokenFields)
                .post(USER_URL + "logout/").then();
    }

}
