package com.azet.vehicle.controller;

import com.azet.vehicle.VehicleApplication;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = VehicleApplication.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/setup.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/cleanup.sql")
public class UserControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Test
    public void getUsers() {
        get("http://localhost:" + port + "/v1/users")
            .then()
            .assertThat()
            .statusCode(200)
            .body("$", hasSize(2))
            .body("[0].id", equalTo(1))
            .body("[0].firstName", equalTo("John"))
        ;
    }

    @Test
    public void getUserById() {
        get("http://localhost:" + port + "/v1/user/2")
            .then()
            .assertThat()
            .statusCode(200)
            .body("id", equalTo(2))
            .body("firstName", equalTo("Ben"))
        ;
    }

    @Test
    public void getUserById_NotFound() {
        get("http://localhost:" + port + "/v1/user/999")
            .then()
            .statusCode(NOT_FOUND.value());
    }

    @Test
    public void replaceUser() throws JSONException {
            given()
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .body(new JSONObject()
                    .put("firstName", "first")
                    .put("lastName", "last")
                    .put("email", "first@test.com")
                    .put("roles", new JSONArray("['TEST']"))
                    .toString()
                )
            .when()
                .put("http://localhost:" + port + "/v1/users/2").prettyPeek()
            .then()
                .statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(2))
                    .body("firstName", equalTo("first"))
                    .body("lastName", equalTo("last"))
                    .body("email", equalTo("first@test.com"))
                    .body("roles", contains("TEST"));
    }
}
