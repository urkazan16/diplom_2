import site.nomoreparties.stellarburgers.constants.RandomTestUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.order.OrderFields;
import site.nomoreparties.stellarburgers.order.OrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import site.nomoreparties.stellarburgers.user.UserRegistrationFields;
import site.nomoreparties.stellarburgers.user.UserRequest;

import java.util.List;

import static site.nomoreparties.stellarburgers.constants.ResponseText.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class TestCaseCreateOrderNegative {
    public List<String> ingredients;
    private OrderRequest orderRequest;
    private UserRequest userRequest;
    private UserRegistrationFields userRegistrationFields;
    private String responseId;


    public TestCaseCreateOrderNegative(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Parameterized.Parameters(name = "Создание заказа. Тестовые данные: {0}")
    public static Object[][] getOrder() {
        return new Object[][]{
                {List.of("61c0c5a71d1f82001bdaaa6d1", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa701")},
                {List.of("")},
        };
    }

    @Before
    public void setUp() {
        orderRequest = new OrderRequest();
        userRequest = new UserRequest();
        userRegistrationFields = RandomTestUser.getRandomRegistration();
        responseId = userRequest.regUser(userRegistrationFields).path(ACCESS_TOKEN);
    }

    @After
    public void cancelOrder() {
        if (responseId != "") {
            userRequest.deletingUser(responseId);
        }
    }

    @Test
    @DisplayName("Create order authorization user")
    public void testCreateOrderAuthUser() {
        OrderFields orderFields = new OrderFields(ingredients);
        ValidatableResponse response = orderRequest.createOrder(orderFields, responseId);
        response.assertThat()
                .and()
                .statusCode(500);
    }

    @Test
    @DisplayName("Create order no authorization user")
    public void testCreateOrderNoAuthUser() {
        OrderFields orderFields = new OrderFields(ingredients);
        ValidatableResponse response = orderRequest.createOrder(orderFields, "");
        response.assertThat()
                .and()
                .statusCode(500);
    }

    @Test
    @DisplayName("Order auth user not ingredients")
    public void testCreateOrderAuthUserNotIngredients() {
        OrderFields orderFields = new OrderFields();
        ValidatableResponse response = orderRequest.createOrder(orderFields, responseId);
        response.assertThat()
                .statusCode(400)
                .and()
                .body(MESSAGE, equalTo(INGREDIENT_PROVIDED));
    }
}
