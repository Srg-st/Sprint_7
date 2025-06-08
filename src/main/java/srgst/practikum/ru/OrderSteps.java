package srgst.practikum.ru;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return
                given()
                        .log().all()
                .header("Content-type", "application/json")
                        .body(order)
                        .when()
                .post(Constant.TESTORDERS);

    }

    @Step("Отмена заказа")
    public Response cancelOrder(int trackId) {
        return
                given()
                        .log().all()
                        .header("Content-type", "application/json")
                        .queryParam("track", trackId)
                        .when()
                        .put(Constant.ORDERCANCEL);

    }

    @Step("Получение списка заказов")
     public Response listOrders() {
        return
                given()
                        .log().all()
                        .header("Content-type", "application/json")
                        .when()
                        .get(Constant.TESTORDERS);

    }


}
