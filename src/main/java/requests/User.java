package requests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import url.Uri;

public class User {

    @Step("POST запрос для создания пользователя")
    public static Response create(Object body) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .when().post(Uri.CREATE);
    }

    @Step("POST запрос для логина пользователя")
    public static Response login(Object body) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .when().post(Uri.LOGIN);
    }

    @Step("DELETE запрос для удаления пользователя")
    public static void delete(String userAccessToken) {
        RestAssured
                .given()
                .header("Authorization", userAccessToken)
                .when()
                .delete(String.format(Uri.DELETE));
    }

    @Step("PATCH запрос для редактирования пользователя после авторизации")
    public static Response editWithToken(String userAccessToken, Object body) {
        return RestAssured
                .given()
                .header("Authorization", userAccessToken)
                .body(body)
                .when()
                .patch(Uri.PATCH);
    }

    @Step("PATCH запрос для редактирования пользователя без авторизации")
    public static Response editWithoutToken(Object body) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .and()
                .body(body)
                .when()
                .patch(Uri.PATCH);
    }
}
