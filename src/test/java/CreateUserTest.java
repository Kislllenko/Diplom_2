import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import requests.UserRequests;
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

        UserRequests.create(createUser).then().statusCode(200);

    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Если создать пользователя с логином или email, которые уже есть, возвращается ошибка")
    public void notAbleCreateTheSameUser() {
        UserRequests.create(createUser);

        Response createRes = UserRequests.create(createUser);
        createRes.then()
                .statusCode(403)
                .and()
                .body("message", equalTo("User already exists"));

    }

    @Test
    @DisplayName("Создание пользователя с одним не заполненным обязательным полем")
    @Description("Если одного из полей нет, запрос возвращает ошибку")
    public void forUserCreationAllFieldsRequired() {
        Response createRes = UserRequests.create(createWithEmptyField);
        createRes.then()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void delete () {

        String userAccessToken = UserRequests.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        if (userAccessToken != null){
        UserRequests.delete(userAccessToken);
        }
    }

}
