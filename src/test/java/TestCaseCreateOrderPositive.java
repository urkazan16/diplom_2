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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TestCaseCreateOrderPositive {

    private OrderRequest orderRequest;
    private UserRequest userRequest;
    private UserRegistrationFields userRegistrationFields;
    private IngredientRequest ingredientRequest;
    private String responseId;
    private List<String> responseIngredient;
    private OrderFields orderFields;

    @Before
    public void setUp() {
        orderRequest = new OrderRequest();
        userRequest = new UserRequest();
        ingredientRequest = new IngredientRequest();
        userRegistrationFields = RandomTestUser.getRandomRegistration();
        responseId = userRequest.regUser(userRegistrationFields).path(ACCESS_TOKEN);
        responseIngredient =  ingredientRequest.getIngredient().path(ORDER_ID);
        orderFields = new OrderFields();
        orderFields.setIngredients(responseIngredient);
    }

    @After
    public void cancelOrder() {
        if (!responseId.equals("")) {
            userRequest.deletingUser(responseId);
        }
    }

    @Test
    @DisplayName("Create order authorization user")
    public void testCreateOrderAuthUser() {
        ValidatableResponse response = orderRequest.createOrder(orderFields, responseId);
        response.assertThat()
                .and()
                .statusCode(200)
                .and().body(SUCCESS, equalTo(true));
    }

    @Test
    @DisplayName("Create order authorization user get ingredients")
    public void testCreateOrderAuthUserOrdersIngredients() {
        ValidatableResponse response = orderRequest.createOrder(orderFields, responseId);
        response.assertThat()
                .and()
                .statusCode(200)
                .and().body(ORDER_INGREDIENTS, notNullValue());
    }

    @Test
    @DisplayName("Create order no authorization user")
    public void testCreateOrderNoAuthUser() {
        ValidatableResponse response = orderRequest.createOrder(orderFields, "");
        response.assertThat()
                .and()
                .statusCode(200)
                .and().body(ORDER, notNullValue());
    }

}
