package org.example.l13_3;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {
    private final int nullPet = 1567898;


    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void search() {
        given().when()
                .get(baseURI + "/pet/{petId}", nullPet)
                .then()
                .statusCode(404)
                .log().all()
                .statusLine("HTTP/1.1 404 Not Found")
                .body("type", equalTo("error"))
                .body("message", equalTo("Pet not found"));
    }

}
