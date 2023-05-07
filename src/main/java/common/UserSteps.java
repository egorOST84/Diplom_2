package common;

import configs.EndPoints;
import io.qameta.allure.Step;
import pojo.user.User;

import static io.restassured.RestAssured.given;
import static specs.RestAssuredSpecs.*;

public class UserSteps {
    @Step("Get user data and verify response | authorized")
    public void getUserAndVerifyResponse(User user, String accessToken) {
        given()
                .header("Authorization", accessToken)
                .when()
                .get(EndPoints.USER_ENDPOINT)
                .then()
                .spec(successGetUserResponse(user.getEmail(), user.getName()));
    }

    @Step("Check user is not found")
    public void checkUserNotFound(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .when()
                .get(EndPoints.USER_ENDPOINT)
                .then()
                .spec(userNotFoundResponse());
    }

    @Step("Delete user")
    public void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .when()
                .delete(EndPoints.USER_ENDPOINT)
                .then()
                .spec(successDeleteRegisteredUserResponse());
    }

    @Step("Delete user")
    public void deleteRegisteredUser(String accessToken) {
        deleteUser(accessToken);
        checkUserNotFound(accessToken);
    }
}