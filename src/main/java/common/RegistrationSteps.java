package common;

import configs.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.AuthResponse;
import pojo.user.User;

import static io.restassured.RestAssured.given;
import static specs.RestAssuredSpecs.failureUserResponse;
import static specs.RestAssuredSpecs.successUserResponse;

public class RegistrationSteps {
    @Step("Send registration user request and get response")
    public Response sendRegistrationUserRequestAndGetResponse(User user) {
        return given()
                .when()
                .body(user)
                .post(EndPoints.REGISTRATION_ENDPOINT)
                .then()
                .spec(failureUserResponse())
                .extract().response();
    }

    @Step("Registration new user")
    public void registrationNewUserAndVerifyResponse(User user) {
        given()
                .when()
                .body(user)
                .post(EndPoints.REGISTRATION_ENDPOINT)
                .then()
                .spec(successUserResponse(user.getEmail(), user.getName()))
                .extract().response().as(AuthResponse.class);
    }
}