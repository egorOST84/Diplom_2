package common;

import configs.EndPoints;
import constants.ErrorMessage;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static specs.RestAssuredSpecs.*;

public class BaseSteps {
    @Step("Send registration user request and get response")
    public Response sendRegistrationUserRequestAndGetResponse(User user) {

        Response response =
                given()
                .when()
                    .body(user)
                    .post(EndPoints.REGISTRATION_ENDPOINT)
                .then()
                    .spec(failureUserResponse())
                    .extract().response();

        return response;
    }

    @Step("Send user login request and get response")
    public Response sendLoginUserRequestAndGetResponse(User user) {

        Response response =
                given()
                .when()
                    .body(user)
                    .post(EndPoints.LOGIN_ENDPOINT)
                .then()
                    .spec(failureUserResponse())
                    .extract().response();

        return response;
    }

    @Step("Send user update request and get response")
    public Response sendPatchUserRequestAndGetResponse(User user, String newEmail, String newName, String accessToken) {

        User updatedUser = new User();
        updatedUser.setEmail(newEmail);
        updatedUser.setName(newName);

        Response response =
                given()
                    .header("Authorization", accessToken)
                .when()
                    .body(updatedUser)
                    .patch(EndPoints.USER_ENDPOINT)
                .then()
                    .spec(failureUserResponse())
                .extract().response();

        return response;
    }

    @Step("Send order create request and get response")
    public Response sendPostCreateOrderAndGetResponse(OrderRequest orderReqJson, String accessToken) {
        Response response =
                given()
                    .header("Authorization", accessToken)
                .when()
                    .body(orderReqJson)
                    .post(EndPoints.ORDER_ENDPOINT)
                .then()
                    .extract().response();

        return response;
    }

    @Step("Send user update request and get response")
    public Response sendGetOrderListAndGetResponse(String accessToken) {
        Response response =
                given()
                    .header("Authorization", accessToken)
                .when()
                    .get(EndPoints.ORDER_ENDPOINT)
                .then()
                    .extract().response();
        return response;
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

    @Step("User authentication")
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

        String accessToken =
                given()
                .when()
                    .body(user)
                    .post(EndPoints.LOGIN_ENDPOINT)
                .then()
                    .spec(successUserResponse(user.getEmail(), user.getName()))
                    .extract().path("accessToken");

        return accessToken;
    }

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


    @Step("Create a new order")
    public void createNewOrderAndVerifyResponse(OrderRequest orderReqJson, String accessToken) {

                given()
                    .header("Authorization", accessToken)
                .when()
                    .body(orderReqJson)
                    .post(EndPoints.ORDER_ENDPOINT)
                .then()
                    .spec(successOrderResponse())
                    .extract().response().as(OrderResponse.class);
    }

    @Step("Get order list")
    public void getOrderListAndVerifyResponse( String accessToken) {
                given()
                    .header("Authorization", accessToken)
                .when()
                    .get(EndPoints.ORDER_ENDPOINT)
                .then()
                    .spec(successOrderListResponse())
                .extract().response().as(OrderList.class);
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
