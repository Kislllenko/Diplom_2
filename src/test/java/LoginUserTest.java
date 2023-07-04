import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import requests.UserRequests;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@DisplayName("Логин пользователя")
public class LoginUserTest extends BaseTest {

    @Test
    @DisplayName("Логин под существующим пользователем")
    @Description("Запрос возвращает правильный код ответа — 200. Успешный запрос возвращает тело с \"AccessToken\", " +
            "который необходим для удаления юзера")
    public void userCouldLogin() {
        Response loginRes = UserRequests.login(createUser);

        loginRes.then().statusCode(200).and().body("$", hasKey("accessToken"));
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void wrongUserReturnsError() {
        Response loginRes = UserRequests.login(wrongUser);
        loginRes.then().statusCode(401).and().body("message", equalTo("email or password are incorrect"));
    }

}
