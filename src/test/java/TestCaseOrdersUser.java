import site.nomoreparties.stellarburgers.constants.RandomTestUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.ingredient.IngredientRequest;
import site.nomoreparties.stellarburgers.order.OrderFields;
import site.nomoreparties.stellarburgers.order.OrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.user.UserRegistrationFields;
import site.nomoreparties.stellarburgers.user.UserRequest;
import java.util.List;

import static site.nomoreparties.stellarburgers.constants.ResponseText.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class TestCaseOrdersUser {

    private UserRequest userRequest;
    private UserRegistrationFields userRegistrationFields;
    private OrderRequest orderRequest;
    private OrderFields orderFields;
    private IngredientRequest ingredientRequest;
    private String token;
    private List<String> responseIngredient;

    @Before
    public void setUp() {
        userRequest = new UserRequest();
        ingredientRequest = new IngredientRequest();
        userRegistrationFields = RandomTestUser.getRandomRegistration();
        token = userRequest.regUser(userRegistrationFields).path(ACCESS_TOKEN);
        orderRequest = new OrderRequest();
        responseIngredient =  ingredientRequest.getIngredient().path(ORDER_ID);
        orderFields = new OrderFields();
        orderFields.setIngredients(responseIngredient);
    }

    @After
    public void clearDate() {
        if (token != null && !token.isBlank()){
            userRequest.deletingUser(token);
        }
    }


    @Test
    @DisplayName("Receiving orders from an authorized user")
    public void testOrderAuthUserTest() {
        orderRequest.createOrder(orderFields, token);
        ValidatableResponse response = orderRequest.getOrder(token);
        response.assertThat()
                .statusCode(200)
                .and()
                .body(ORDERS_INGREDIENTS,notNullValue());
    }

    @Test
    @DisplayName("User orders id")
    public void testOrderAuthUser() {
        orderRequest.createOrder(orderFields, token);
        ValidatableResponse response = orderRequest.getOrder(token);
        response.assertThat()
                .statusCode(200)
                .and()
                .body(SUCCESS, equalTo(true));
    }

        @Test
    @DisplayName("Message not auth user")
    public void testOrderNotAuthUser() {
        orderRequest.createOrder(orderFields, token);
        ValidatableResponse response = orderRequest.getOrder("");
        response.assertThat()
                .statusCode(401)
                .and()
                .body(MESSAGE, equalTo(NOT_AUTHORISED));
    }

}
