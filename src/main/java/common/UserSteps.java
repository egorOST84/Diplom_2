package common;

import configs.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
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

    @Step("Send user update request and get response")
    public Response sendPatchUserRequestAndGetResponse(User user, String newEmail, String newName, String accessToken) {

        User updatedUser = new User();
        updatedUser.setEmail(newEmail);
        updatedUser.setName(newName);

        return given()
                .header("Authorization", accessToken)
                .when()
                .body(updatedUser)
                .patch(EndPoints.USER_ENDPOINT)
                .then()
                .spec(failureUserResponse())
                .extract().response();
    }

    @Step("Change user email and name to new values")
    public void updateUserEmailAndName(User user, String newEmail, String newName, String accessToken) {
        user.setEmail(newEmail);
        user.setName(newName);

        given()
                .header("Authorization", accessToken)
                .when()
                .body(user)
                .patch(EndPoints.USER_ENDPOINT)
                .then()
                .spec(successGetUserResponse(user.getEmail(), user.getName()));
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