package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.code_api_data.Orders;
import org.example.code_api_data.User;
import org.example.code_api_data.UserResponse;
import org.example.pojo.CreateOrdersJson;
import org.example.pojo.CreateUserJson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Получение заказов")
public class GetUsersOrderTest {

    CreateUserJson createUser = new CreateUserJson("kislenko-s@yandex.ru", "1Qwe%", "Kislenko");

    private final String[] INGREDIENTS = {"61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f"};

    @Before
    public void setUp() {

        User.create(createUser);

    }

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

    @After
    public void tearDown() {

        String userAccessToken = User.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        User.delete(userAccessToken);
    }
}
