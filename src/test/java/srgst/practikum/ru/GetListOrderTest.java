package srgst.practikum.ru;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetListOrderTest {
    private OrderSteps orderSteps;

    @Before
    public void setUp(){
        orderSteps = new OrderSteps();
        RestAssured.baseURI = Constant.TESTURL;
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что при запросе списка заказов тело ответа содержит список заказов")
    public void getListOrder() {
        orderSteps.listOrders()
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());

    }
}
