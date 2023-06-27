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


@DisplayName("Изменение данных пользователя")
public class EditUserDataTest {

    CreateUserJson createUser = new CreateUserJson("kislenko-s@yandex.ru", "1Qwe%", "Kislenko");

    CreateUserJson updateUser = new CreateUserJson("kislenkoSY@yandex.ru", "Qe%13", "KislenkoS");

    @Before
    public void setUp() {
        User.create(createUser);
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Данные успешно изменены код ответа 200")
    public void editUserData() {
        User.login(createUser);

        String userAccessToken = User.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        Response response = User.editWithToken(userAccessToken, updateUser);
        response.then().assertThat().statusCode(200).and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Если попытаться изменить данные без авторизации, то запрос возвращает ошибку 401")
    public void editUserDataWithoutToken() {

        User.editWithoutToken(updateUser)
                .then()
                .statusCode(401).and()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));

    }

    @After
    public void tearDown() {

        String userAccessToken = User.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        User.delete(userAccessToken);
    }

}
