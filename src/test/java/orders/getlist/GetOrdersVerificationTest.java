package orders.getlist;

import constants.ErrorMessage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import common.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.Test;
import pojo.order.OrderRequest;
import pojo.user.User;

import static utilities.OrderGenerator.generateRandomOrder;
import static utilities.UserGenerator.generateRandomUser;

@Feature("[Get orders] GET api/orders")
public class GetOrdersVerificationTest extends BaseTest {

    /**
     * Этот тест проверяет, что после авторизации пользователь может получить список своих заказов.
     */
    @Test
    @DisplayName("Get orders | authorized")
    @Description("This test verifies that  after authorization")
    @Severity(SeverityLevel.BLOCKER)
    public void checkCreateOrderForAuthorizedUser() {
        // Регистрируем нового пользователя
        User rndUser = generateRandomUser();
        registrationSteps.registrationNewUserAndVerifyResponse(rndUser);
        // Получаем access token
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        // Создаем заказ с авторизацией и ожидаем успешный ответ
        OrderRequest orderReq = generateRandomOrder(2);
        orderSteps.createNewOrderAndVerifyResponse(orderReq, accessToken);
        // Проверяем что список заказов успешно получен
        orderSteps.getOrderListAndVerifyResponse(accessToken);
        // Удаляем пользователя
        userSteps.deleteRegisteredUser(accessToken);
    }

    /**
     * Этот тест проверяет, что для неавторизованного пользователя невозможно получить список заказов.
     */
    @Test
    @DisplayName("Get orders | unauthorized")
    @Description("Verify that unauthorized user cannot get order list")
    @Severity(SeverityLevel.BLOCKER)
    public void checkGetOrderListUnauthorizedUser() throws Exception {
        // Получаем список заказов без авторизации и ожидаем код ответа 401 Unauthorized
        Response response = orderSteps.sendGetOrderListAndGetResponse("");
        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED);
        baseSteps.checkErrorMessage(response, ErrorMessage.GET_ORDER_UNAUTHORIZED_ERROR_401);
    }
}