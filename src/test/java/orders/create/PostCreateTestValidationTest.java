package orders.create;

import constants.ErrorMessage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import common.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.Test;
import pojo.order.OrderRequest;

import java.util.List;

import static utilities.OrderGenerator.generateRandomOrder;

@Feature("[Create order] POST api/orders - validation / negative")
public class PostCreateTestValidationTest extends BaseTest {

    /**
     * Этот тест проверяет, что заказ не может быть создан без указания ингредиентов.
     */
    @Test
    @DisplayName("Create order / \"without ingredients\" validation / negative")
    @Description("Verify that order cannot be created without ingredients")
    @Severity(SeverityLevel.CRITICAL)
    public void validateCreateOrderWithoutIngredients() throws Exception {
        // Попытка создать заказ без ингредиентов
        OrderRequest orderReq = generateRandomOrder(2);
        orderReq.setIngredients(List.of());
        Response response = baseSteps.sendPostCreateOrderAndGetResponse(orderReq, "");
        // Проверяем, что код ответа 400 Bad request, а сообщение ответа соответствует ожидаемому сообщению об ошибке.
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
        baseSteps.checkErrorMessage(response, ErrorMessage.CREATE_ORDER_WITHOUT_INGREDIENTS_ERROR_400);
    }

    /**
     * Данный тест проверяет валидацию хешей ингредиентов при создании заказа.
     */
    @Test
    @DisplayName("Create order / \"invalid ingredient hash\" validation / negative")
    @Description("Verify that order cannot be created with invalid ingredient hash")
    @Severity(SeverityLevel.CRITICAL)
    public void validateCreateOrderWithInvalidIngredientHash() {
        // Попытка создать заказ без ингредиентов
        OrderRequest orderReq = generateRandomOrder(3);
        orderReq.setIngredients(List.of("invalid hash", "_@#$%&^!%", ""));
        Response response = baseSteps.sendPostCreateOrderAndGetResponse(orderReq, "");
        // Проверяем, что код ответа 500 Internal error
        response.then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        response.then().contentType(ContentType.HTML);
    }
}
