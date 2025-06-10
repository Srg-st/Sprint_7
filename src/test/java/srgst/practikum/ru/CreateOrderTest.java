package srgst.practikum.ru;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private String[] color;
    private Order order;
    private OrderSteps orderSteps;
    private Integer track;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] createData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"GREY", "BLACK"}},
                {new String[]{}},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Constant.TEST_URL;
        orderSteps = new OrderSteps();
        order = new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", null);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с указанными параметрами параметризации")
    public void testCreateOrder() {
        order.setColor(color);
        Response response = orderSteps.createOrder(order);
        response.then()
                .log()
                .all().assertThat().statusCode(201)
                .and()
        .body("track", notNullValue());
        track = response.then().extract().path("track");

    }

    @After
    public void tearDown() {
        if (track != null) {
            orderSteps.cancelOrder(track);
        }
    }
}
