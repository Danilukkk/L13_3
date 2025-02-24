package org.example.l13_3;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {
    private static final Integer nullPet = 1567898;


    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @BeforeAll
    public static void del() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        Integer petId = nullPet;

        int statusCode = given()
                .header("api_key", "special-key")
                .when()
                .delete("/pet/{petId}", petId)
                .then()
                .log().all()
                .extract()
                .statusCode();

        if (statusCode == 404) {
            System.out.println("Питомец уже отсутствует в базе, DELETE не требуется.");
        } else if (statusCode == 200) {
            System.out.println("Питомец успешно удален.");
        } else {
            System.out.println("Неожиданный статус ответа: " + statusCode);
        }
    }


    @Test
    @DisplayName("Get запрос отвечает ошибкой 404 при отсутствии питомца")
    public void search() {
        given().when()
                .get(baseURI + "/pet/{petId}", nullPet)
                .then()
                .statusCode(404)
                .log().all()
                .statusLine("HTTP/1.1 404 Not Found")
                .body("message", equalTo("Pet not found"), "type", equalTo("error"));
    }

    @Test
    @DisplayName("Добавление нового питомца")
    public void newPetTest() {
        Integer id = nullPet;
        String name = "dogg";
        String status = "sold";

        Map<String, String> req = new HashMap<>();
        req.put("id", id.toString());
        req.put("name", name);
        req.put("status", status);

        given().contentType("application/json")
                .body(req)
                .when()
                .post(baseURI + "/pet")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(id), "name", equalTo(name), "status", equalTo(status));
    }

    @Test
    @DisplayName("Создаем нового пользователя, проверяем соответствие id и код ответа")
    public void addNewUser() {
        Integer id = 78094;
        String username = "Danilukkk";
        String firstName = "Daniil";
        String lastName = "Fedyashov";
        String email = "df@m.com";
        String password = "asdfgh";
        String phone = "123456789";
        Integer userStatus = 1;

        Map<String, String> request = new HashMap<>();
        request.put("id", id.toString());
        request.put("username", username);
        request.put("firstName", firstName);
        request.put("lastName", lastName);
        request.put("email", email);
        request.put("password", password);
        request.put("phone", phone);
        request.put("userStatus", userStatus.toString());

        given().contentType("application/json")
                .body(request)
                .when()
                .post(baseURI + "/user")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo(id.toString()));
    }
}
