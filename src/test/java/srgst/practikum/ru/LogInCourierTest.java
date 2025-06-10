package srgst.practikum.ru;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

public class LogInCourierTest {
    private String login;
    private String password;
    private CourierSteps courierStepsLogin;
    private Integer id;
    private Courier courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = Constant.TEST_URL;
        login = RandomStringUtils.randomAlphanumeric(10);
        password = RandomStringUtils.randomAlphanumeric(10);
        courierStepsLogin = new CourierSteps();
        courierStepsLogin.createNewCourier(new Courier(login, password, null))
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка возможности залогиниться для курьера")
    public void courierLoginTest() {
        courierStepsLogin.loginCourierAndGetId(new Courier(login, password,null))
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка возможности залогиниться курьеру без логина")
    public void courierLoginTestWithoutLogin() {
        courierStepsLogin.loginCourierAndGetId(new Courier("", password,null))
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка возможности залогиниться курьеру без пароля")
    public void courierLoginTestWithoutPassword() {
        courierStepsLogin.loginCourierAndGetId(new Courier(login, "",null))
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка возможности залогиниться курьеру с неправильным логином")
    public void courierLoginTestWithWrongLogin() {
        courierStepsLogin.loginCourierAndGetId(new Courier(login+"1", password,null))
                .then()
                .log().all()
                .assertThat().statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка возможности залогиниться курьеру с неправильным паролем")
    public void courierLoginTestWithWrongPassword() {
        courierStepsLogin.loginCourierAndGetId(new Courier(login, password+"1",null))
                .then()
                .log().all()
                .assertThat().statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка возможности залогиниться курьеру с несуществующим логином")
    public void courierLoginTestWithNotExistentLogin() {
        courierStepsLogin.loginCourierAndGetId(new Courier("AnyLogin", password,null))
                .then()
                .log().all()
                .assertThat().statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка возможности залогиниться курьеру с несуществующим паролем")
    public void courierLoginTestWithNotExistentPassword() {
        courierStepsLogin.loginCourierAndGetId(new Courier(login, "AnyPassword",null))
                .then()
                .log().all()
                .assertThat().statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void cleanUp() {
        courier = new Courier(login, password, null);
        if (id != null) {
            Integer id = courierStepsLogin.loginCourierAndGetId(courier)
                    .then()
                    .extract()
                    .body()
                    .path("id");
            courier.withId(id);
            courierStepsLogin.deleteCourier(courier);
        }
    }

}



