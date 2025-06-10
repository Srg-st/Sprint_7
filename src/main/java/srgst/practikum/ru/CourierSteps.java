package srgst.practikum.ru;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class CourierSteps {

    @Step("Создание нового курьера")
    public Response createNewCourier(Courier courier) {
        return
                given()
                .log().all()
                        .contentType(ContentType.JSON)
                        .body(courier)
                .when()
                .post(Constant.TEST_COURIER);

    }

    @Step("Авторизация курьера и получение его ID")
    public Response loginCourierAndGetId(Courier courier) {
        return
                given()
                .log().all()
                        .contentType(ContentType.JSON)
                        .body(courier)
                .when()
                .post(Constant.TEST_COURIER_LOGIN);
    }

    @Step("Удаление курьера")
    public Response deleteCourier(Courier courier) {
        return
                given()
                .log().all()
                        .contentType(ContentType.JSON)
                        .pathParam("id", courier.getId())
                .when()
                .delete(Constant.COURIER_DELETE);
    }
}
