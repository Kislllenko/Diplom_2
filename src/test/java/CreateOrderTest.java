import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import requests.Orders;
import requests.User;
import requests.UserResponse;
import pojo.CreateOrdersJson;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Создание заказа")
public class CreateOrderTest extends BaseTest{

    @Test
    @DisplayName("Создание заказа с авторизацией и с ингредиентами")
    @Description("Заказ успешно создан код ответа 200 OK")
    public void createOrder() {

        User.login(createUser);

        String userAccessToken = User.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        Response response = Orders.createOrder(new CreateOrdersJson(INGREDIENTS), userAccessToken);
        response.then().assertThat().statusCode(200).and().body("name", equalTo(BURGER_NAME));
    }

    @Test
    @DisplayName("Создание заказа без авторизации и с ингредиентами")
    @Description("Заказ успешно создан код ответа 200 OK")
    public void createOrderWithoutAuthorization() {

        Response response = Orders.createOrderWithoutAuthorization(new CreateOrdersJson(INGREDIENTS));
        response.then().assertThat().statusCode(200).and().body("name", equalTo(BURGER_NAME));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Заказ не создан код ответа 400 Bad Request")
    public void createOrderWithoutIngredients() {

        User.login(createUser);

        String userAccessToken = User.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        Response response = Orders.createOrder(new CreateOrdersJson(null), userAccessToken);
        response.then().assertThat().statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа c невалидным хешем ингредиента")
    @Description("Заказ не создан код ответа 500 Internal Server Error")
    public void createOrderWithWrongHash() {

        User.login(createUser);

        String userAccessToken = User.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        Response response = Orders.createOrder(new CreateOrdersJson(new String[]{"24323"}), userAccessToken);
        response.then().assertThat().statusCode(500);
    }

}
