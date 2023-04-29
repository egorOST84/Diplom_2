package common;

import configs.EndPoints;
import constants.ErrorMessage;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import pojo.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static specs.RestAssuredSpecs.*;

public class BaseSteps {
    private String accessToken;
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
    public Response sendPatchUserRequestAndGetResponse(User user, String newEmail, String newName, String accessToken) throws Exception {

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
    @Step("Registration new user and get access token")
    public String registrationNewUserAndGetToken(User user) {

        String accessToken =
                given()
                .when()
                    .body(user)
                    .post(EndPoints.REGISTRATION_ENDPOINT)
                .then()
                    .spec(successUserResponse(user.getEmail(), user.getName()))
                    .extract().path("accessToken");

        return accessToken;
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
    public String loginUserAndGetToken(User user) throws Exception {

        String accessToken =
                given()
                .when()
                    .body(user)
                    .post(EndPoints.LOGIN_ENDPOINT)
                .then()
                    .spec(successUserResponse(user.getEmail(), user.getName()))
                    .extract().path("accessToken");

        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException("Access token is not found in the response body");
        }

        return accessToken;
    }

    @Step("Get user data and verify response | authorized")
    public void getUserAndVerifyResponse(User user, String accessToken) throws Exception {
            given()
                .header("Authorization", accessToken)
            .when()
                .get(EndPoints.USER_ENDPOINT)
            .then()
                .spec(successGetUserResponse(user.getEmail(), user.getName()));
    }

    @Step("Change user email and name to new values")
    public void updateUserEmailAndName(User user, String newEmail, String newName, String accessToken) throws Exception{
            given()
                .header("Authorization", accessToken)
            .when()
                .body(user.setEmail(newEmail))
                .body(user.setName(newName))
                .patch(EndPoints.USER_ENDPOINT)
            .then()
                .spec(successGetUserResponse(user.getEmail(), user.getName()));
    }


    @Step("Delete user")
    public void deleteRegisteredUser(User user, String accessToken) throws Exception {
            given()
                .header("Authorization", accessToken)
            .when()
                .delete(EndPoints.USER_ENDPOINT)
            .then()
                .spec(successdeleteRegisteredUserResponse());
    }

    @Step("Create a new order")
    public OrderResponse createNewOrder(String accessToken, OrderRequest orderReqJson) {

        OrderResponse orderResponse =
                given()
                    .auth().oauth2(accessToken)
                .when()
                    .body(orderReqJson)
                    .post(EndPoints.ORDER_ENDPOINT)
                .then()
                    .assertThat().body("success", equalTo(true))
                    .extract().response().as(OrderResponse.class);

        return orderResponse;
    }



//    @Step("Get order track number")
//    public OrderCancelRequest createNewOrderAndGetTrack(OrderRequest orderReq) {
//
//        OrderCancelRequest orderCancelRequest =
//                given()
//                .when()
//                    .body(orderReq)
//                    .post(EndPoints.orders)
//                .then()
//                    .assertThat().body("track", notNullValue())
//                .extract().response().as(OrderCancelRequest.class);
//        return orderCancelRequest;
//    }
//
//    @Step("Cancel existing order by track number")
//    public void cancelOrder(OrderCancelRequest jsonBody) {
//
//                given()
//                .when()
//                    .body(jsonBody)
//                    .put(EndPoints.orderCancel)
//                .then()
//                    .statusCode(HttpStatus.SC_OK);
//    }
//    @Step("Get order list")
//    public OrderListResponse getOrderList() {
//
//        OrderListResponse orderListResponse =
//                given()
//                .when()
//                    .get(EndPoints.orders)
//                .then()
//                    .extract().response().as(OrderListResponse.class);
//        return orderListResponse;
//    }
//
//    @Step("Get order list with params")
//    public Response getOrderListWithParams(Integer courierId, String nearestStation, Integer limit, Integer page) {
//
//        RequestSpecification requestSpec = given()
//                .queryParams("courierId", courierId != null ? courierId : "")
//                .queryParams("nearestStation", nearestStation != null ? nearestStation : "")
//                .queryParams("limit", limit != null ? limit : "")
//                .queryParams("page", page != null ? page : "");
//
//        Response response = requestSpec.get(EndPoints.orders).then().extract().response();
//        return response;
//    }
//
//
    @Step ("Check response for invalid parameters")
    public void checkResponse(Response response, ErrorMessage errorMessage) throws Exception {

        String message = errorMessage.getDescription();

        switch (response.statusCode()) {
            case HttpStatus.SC_UNAUTHORIZED:
            case HttpStatus.SC_FORBIDDEN:
                response.then().assertThat().body("message", equalTo(message));
                break;
            default:
                throw new Exception("Unknown status code or message");
        }
    }


}
