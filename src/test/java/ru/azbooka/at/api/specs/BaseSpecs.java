package ru.azbooka.at.api.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import ru.azbooka.at.utils.allure.report.AllureListener;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class BaseSpecs {

    public static RequestSpecification requestSpec() {
        return given()
                .filter(AllureListener.withCustomTemplates())
                .contentType(JSON);
    }

    public static RequestSpecification requestSpec(String token) {
        return requestSpec()
                .auth().oauth2(token);
    }

    public static ResponseSpecification responseSpec(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .build();
    }
}
