package srgst.practikum.ru;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;



public class CreateCourierTest {
    private String login;
    private String password;
    private String firstName;
    private Courier courier;
    private CourierSteps courierSteps;
    private Integer id;

    @Before
    public void setUp() {
        RestAssured.baseURI = Constant.TESTURL;
        courierSteps = new CourierSteps();
        login = RandomStringUtils.randomAlphanumeric(10);
        password = RandomStringUtils.randomAlphanumeric(10);
        firstName = RandomStringUtils.randomAlphanumeric(10);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка создания курьера с помощью ручки /api/v1/courier")
    public void createCourierTest() {
        courierSteps.createNewCourier(new Courier(login, password, firstName))
                .then()
                .log().all()
        .assertThat().statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка невозможности создания двух одинаковых курьеров с помощью ручки /api/v1/courier")
    public void createCourierTwiceTest() {
        courierSteps.createNewCourier(new Courier(login, password, firstName))
                .then()
                .log().all()
        .assertThat().statusCode(201)
                .body("ok", equalTo(true));

        courierSteps.createNewCourier(new Courier(login, password, firstName))
                .then()
                .log().all()
        .assertThat().statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));

    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка создания курьера с помощью ручки /api/v1/courier без логина")
    public void createCourierWithoutLoginTest() {
        courierSteps.createNewCourier(new Courier(null, password, firstName))
                .then()
                .log().all()
        .assertThat().statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка создания курьера с помощью ручки /api/v1/courier без пароля")
    public void createCourierWithoutPasswordTest() {
        courierSteps.createNewCourier(new Courier(login, null, firstName))
                .then()
                .log().all()
        .assertThat().statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка создания курьера с помощью ручки /api/v1/courier без имени")
    public void createCourierWithoutFirstNameTest() {
        courierSteps.createNewCourier(new Courier(login, password, null))
                .then()
                .log().all()
        .assertThat().statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка создания курьера с помощью ручки /api/v1/courier без логина и пароля")
    public void createCourierWithoutLoginAndPasswordTest() {
        courierSteps.createNewCourier(new Courier(null, null, firstName))
                .then()
                .log().all()
        .assertThat().statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка создания курьера с помощью ручки /api/v1/courier без полей")
    public void createCourierWithoutFieldsTest() {
        courierSteps.createNewCourier(new Courier(null, null, null))
                .then()
                .log().all()
        .assertThat().statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }



    @After
    public void deleteCourier() {
        courier = new Courier(login, password, firstName);
        if (id != null) {
            Integer id = courierSteps.loginCourierAndGetId(courier)
                    .then()
                    .extract()
                    .body()
                    .path("id");
            courier.withId(id);
            courierSteps.deleteCourier(courier);
        }
    }



}
