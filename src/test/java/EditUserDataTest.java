import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import requests.User;
import requests.UserResponse;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Изменение данных пользователя")
public class EditUserDataTest extends BaseTest {

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

}
