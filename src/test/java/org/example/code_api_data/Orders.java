package org.example.code_api_data;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Step;
import org.example.pojo.CreateOrdersJson;
import org.example.url.Uri;

public class Orders {

    @Step("POST запрос для создания заказа c авторизацией")
    public static Response createOrder(CreateOrdersJson body, String userAccessToken) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Authorization", userAccessToken)
                .body(body)
                .when().post(Uri.ORDER_PATH);

    }

    @Step("POST запрос для создания заказа без авторизации")
    public static Response createOrderWithoutAuthorization(CreateOrdersJson body) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .when().post(Uri.ORDER_PATH);

    }

    @Step("GET запрос для получения заказа с авторизованным пользователем")
    public static Response getOrders(String userAccessToken) {
        return RestAssured
                .given()
                .header("Authorization", userAccessToken)
                .when().get(Uri.ORDER_PATH);
    }

    @Step("GET запрос для получения заказа с не авторизованным пользователем")
    public static Response getOrdersWithoutAuthorization() {
        return RestAssured
                .when().get(Uri.ORDER_PATH);
    }
}
