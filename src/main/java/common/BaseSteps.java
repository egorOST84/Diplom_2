package common;

import configs.EndPoints;
import constants.ErrorMessage;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.user.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static specs.RestAssuredSpecs.*;

public class BaseSteps {
    @Step("Send user login request and get response")
    public Response sendLoginUserRequestAndGetResponse(User user) {
        return given()
                .when()
                    .body(user)
                    .post(EndPoints.LOGIN_ENDPOINT)
                .then()
                    .spec(failureUserResponse())
                    .extract().response();
    }

    @Step("User login")
    public void loginUser(User user) {
                given()
                .when()
                    .body(user)
                    .post(EndPoints.LOGIN_ENDPOINT)
                .then()
                    .spec(successUserResponse(user.getEmail(), user.getName()));
    }

    @Step("User login and get access token")
    public String loginUserAndGetToken(User user) {
        return given()
                .when()
                    .body(user)
                    .post(EndPoints.LOGIN_ENDPOINT)
                .then()
                    .spec(successUserResponse(user.getEmail(), user.getName()))
                    .extract().path("accessToken");
    }

    @Step ("Check response for invalid parameters")
    public void checkErrorMessage(Response response, ErrorMessage errorMessage) throws Exception {

        String message = errorMessage.getDescription();

        try {
            response.then().assertThat().body("message", equalTo(message));
        } catch (Exception e) {
            throw new Exception("Unknown status code or message");
        }
    }
}