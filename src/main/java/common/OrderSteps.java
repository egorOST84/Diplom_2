package common;

import configs.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.order.OrderList;
import pojo.order.OrderRequest;
import pojo.order.OrderResponse;

import static io.restassured.RestAssured.given;
import static specs.RestAssuredSpecs.successOrderListResponse;
import static specs.RestAssuredSpecs.successOrderResponse;

public class OrderSteps {

    @Step("Send order create request and get response")
    public Response sendPostCreateOrderAndGetResponse(OrderRequest orderReqJson, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .body(orderReqJson)
                .post(EndPoints.ORDER_ENDPOINT)
                .then()
                .extract().response();
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

    @Step("Send get order list request and get response")
    public Response sendGetOrderListAndGetResponse(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .get(EndPoints.ORDER_ENDPOINT)
                .then()
                .extract().response();
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
}
