import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import requests.Orders;
import requests.User;
import requests.UserResponse;
import pojo.CreateOrdersJson;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Получение заказов")
public class GetUsersOrderTest extends BaseTest {

    @Test
    @DisplayName("Получение заказов конкретного пользователя. Авторизованный пользователь.")
    @Description("Список заказов получен, код ответа 200 OK")
    public void getUsersOrders() {

        User.login(createUser);

        String userAccessToken = User.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        Orders.createOrder(new CreateOrdersJson(INGREDIENTS), userAccessToken);

        Response response = Orders.getOrders(userAccessToken);
        response.then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя. Не авторизованный пользователь.")
    @Description("Список заказов не получен, код ответа 401 Unauthorized")
    public void getUsersOrdersWithoutAuthorization() {

        User.login(createUser);

        String userAccessToken = User.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        Orders.createOrder(new CreateOrdersJson(INGREDIENTS), userAccessToken);

        Response response = Orders.getOrdersWithoutAuthorization();
        response.then().assertThat().statusCode(401).and().body("message", equalTo("You should be authorised"));
    }

}
