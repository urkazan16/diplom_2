package site.nomoreparties.stellarburgers.ingredient;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.constants.request.Header;

import static io.restassured.RestAssured.given;

public class IngredientRequest extends Header {

    private static final String INGREDIENT_URL = BASE_URL + "ingredients/";

    @Step("Get ingredients")
    public Response getIngredient() {
        return given()
                .spec(getRequestSpec())
                .get(INGREDIENT_URL);
    }
}
