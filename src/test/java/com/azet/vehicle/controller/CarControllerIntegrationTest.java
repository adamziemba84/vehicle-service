package com.azet.vehicle.controller;

import com.azet.vehicle.VehicleApplication;
import com.azet.vehicle.dto.CarCreateDTO;
import com.azet.vehicle.model.Car;
import com.azet.vehicle.repository.CarRepository;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = VehicleApplication.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/car/setup.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/car/cleanup.sql")
public class CarControllerIntegrationTest {
    @LocalServerPort
    private int port;
    private static final String BASE_PATH = "http://localhost:";

    @Autowired
    CarRepository carRepository;

    private String getUrl(String endpoint) {
        return BASE_PATH+port+endpoint;
    }

    @Test
    public void getAllCars() {
        get(getUrl("/v1/car"))
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(10))
                .body("[0].id", equalTo(1))
                .body("[0].auction", equalTo(1))
                .body("[0].manufacturer", equalTo(1))
                .body("[0].model", equalTo(1))
                .body("[0].fuelType", equalTo(1))
                .body("[0].type", equalTo(3))
                .body("[0].vin", equalTo("2BXJBWC19BV000956"))
                .body("[5].id", equalTo(6))
                .body("[5].auction", equalTo(2))
                .body("[5].manufacturer", equalTo(3))
                .body("[5].vin", equalTo("YV1MS390092449965"))
        ;
    }

    @Test
    public void getCarById() {
        get(getUrl("/v1/car/3"))
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(3))
                .body("manufacturer", equalTo(2))
                .body("vin", equalTo("2C4RDGCG9DR595701"));
    }

    @Test
    public void getCarById_NotFound() {
        get(getUrl("/v1/car/999"))
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void createCar() {

        CarCreateDTO carCreateDTO = CarCreateDTO.builder()
                .auction(1)
                .manufacturer(5)
                .model(1)
                .type(1)
                .manufactureYear(2017)
                .mileage(98070)
                .fuelType(Car.FuelType.DIESEL.getValue())
                .vin("11FFVIN999")
                .basicPrice(58000)
                .engineSize(1398)
                .country("PL")
                .build();

            given()
                .contentType(ContentType.JSON)
                .body(carCreateDTO)
            .when()
                .post(getUrl("/v1/car")).prettyPeek()
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", equalTo(14))
                .body("auction", equalTo(1))
                .body("manufacturer", equalTo(5))
                .body("model", equalTo(1))
                .body("fuelType", equalTo(0))
                .body("vin", equalTo("11FFVIN999"))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }

    @Test
    public void createCar_NotValid() {

        CarCreateDTO carCreateDTO = CarCreateDTO.builder()
                .fuelType(3)
                .vin("11FFVIN999")
                .country("PL")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(carCreateDTO)
                .when()
                .post(getUrl("/v1/car")).prettyPeek()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors", hasSize(1))
                .body("message", equalTo("Validation failed for object='carCreateDTO'. Error count: 1"));
    }

    @Test
    public void patchCar(){

        given()
            .contentType("application/json-patch+json")
            .body(patchRequest("replace", "/manufactureYear", "2020")).log().body()
        .when()
            .patch(getUrl("/v1/car/1"))
            .prettyPeek()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("manufactureYear", equalTo(2020));

        assertThat(carRepository.findById(1L).get().getManufactureYear(), equalTo(2020));
    }

    public static String patchRequest(String op, String path, String value) {
        return "[{\"op\":\"" + op + "\",\"path\":\"" + path + "\",\"value\":" + value + "}]";
    }

    @Test
    public void sortedCar() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .post(getUrl("/v1/sortedCars?page=0&size=5&sort=id,asc")).prettyPeek()
                .then()
                .statusCode(HttpStatus.OK.value()).log().all()
                .body("content.$", hasSize(5))
        ;
    }

    @Test
    public void searchCar() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(getUrl("/v1/search?filters=vin:2BXJBWC19BV000956,manufactureYear:2012")).prettyPeek()
                .then()
                .statusCode(HttpStatus.OK.value()).log().all()
                .body("$.", hasSize(3))
        ;
    }
}
