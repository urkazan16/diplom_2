package site.nomoreparties.stellarburgers.order;

import site.nomoreparties.stellarburgers.constants.request.Header;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class OrderRequest extends Header {

    private static final String ORDER_URL = BASE_URL + "orders/";

    @Step("Get order {requestBody}")
    public ValidatableResponse getOrder(String requestBody) {
        return given()
                .spec(getRequestAuthSpec(requestBody))
                .get(ORDER_URL).then();
    }

    @Step("Get order {orderFields}")
    public ValidatableResponse createOrder(OrderFields orderFields, String requestBody) {
        return given()
                .spec(getRequestAuthSpec(requestBody))
                .body(orderFields)
                .post(ORDER_URL).then();
    }

}
