package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.code_api_data.User;
import org.example.code_api_data.UserResponse;
import org.example.pojo.CreateUserJson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@DisplayName("Логин пользователя")
public class LoginUserTest {

    CreateUserJson createUser = new CreateUserJson("kislenko-s@yandex.ru", "1Qwe%", "Kislenko");
    CreateUserJson wrongUser = new CreateUserJson("kislenko-s@yandex.ru", "1Qwe%22", "KislenkoS");

    @Before
    public void setUp() {
        User.create(createUser);
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    @Description("Запрос возвращает правильный код ответа — 200. Успешный запрос возвращает тело с \"AccessToken\", " +
            "который необходим для удаления юзера")
    public void userCouldLogin() {
        Response loginRes = User.login(createUser);

        loginRes.then().statusCode(200).and().body("$", hasKey("accessToken"));
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void wrongUserReturnsError() {
        Response loginRes = User.login(wrongUser);
        loginRes.then().statusCode(401).and().body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void tearDown() {

        String userAccessToken = User.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        User.delete(userAccessToken);
    }


}
