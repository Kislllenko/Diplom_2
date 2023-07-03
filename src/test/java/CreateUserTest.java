import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import requests.User;
import requests.UserResponse;
import pojo.CreateUserJson;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
@DisplayName("Cоздание пользователя")
public class CreateUserTest {

    CreateUserJson createUser = new CreateUserJson("kislenko-s@yandex.ru", "1Qwe%", "Kislenko");
    CreateUserJson createWithEmptyField = new CreateUserJson("", "1Qwe%", "Kislenko");

    @Test
    @DisplayName("Cоздание уникального пользователя")
    @Description("Запрос возвращает правильный код ответа — 200. Успешный запрос возвращает \"ok\": \"true\"")
    public void ableToCreateNewUser() {
        User.create(createUser).then().statusCode(200);

        String userAccessToken = User.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        User.delete(userAccessToken);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Если создать пользователя с логином или email, которые уже есть, возвращается ошибка")
    public void notAbleCreateTheSameUser() {
        User.create(createUser);

        Response createRes = User.create(createUser);
        createRes.then()
                .statusCode(403)
                .and()
                .body("message", equalTo("User already exists"));

        String userAccessToken = User.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        User.delete(userAccessToken);
    }

    @Test
    @DisplayName("Создание пользователя с одним не заполненным обязательным полем")
    @Description("Если одного из полей нет, запрос возвращает ошибку")
    public void forUserCreationAllFieldsRequired() {
        Response createRes = User.create(createWithEmptyField);
        createRes.then()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

}
