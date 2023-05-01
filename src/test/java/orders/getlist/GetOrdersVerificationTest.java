package orders.getlist;

import common.BaseSteps;
import constants.ErrorMessage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import pojo.OrderRequest;
import pojo.User;

import static specs.RestAssuredSpecs.*;
import static utilities.OrderGenerator.generateRandomOrder;
import static utilities.UserGenerator.generateRandomUser;

@Feature("[Get orders] GET api/orders")
public class GetOrdersVerificationTest {
    private static BaseSteps baseSteps;

    @BeforeClass
    public static void setUp() throws Exception {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }

    /**
     * Этот тест проверяет, что после авторизации пользователь может получить список своих заказов.
     */
    @Test
    //@DisplayName("Get orders | authorized")
    @Description("This test verifies that  after authorization")
    @Severity(SeverityLevel.BLOCKER)
    public void checkCreateOrderForAuthorizedUser() throws Exception {
        // Регистрируем нового пользователя
        User rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
        // Получаем access token
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        // Создаем заказ с авторизацией и ожидаем успешный ответ
        OrderRequest orderReq = generateRandomOrder(2);
        baseSteps.createNewOrder(orderReq, accessToken);
        // Проверяем что список заказов успешно получен
        baseSteps.getOrderListAndVerifyResponse(accessToken);
        // Удаляем пользователя
        baseSteps.deleteRegisteredUser(accessToken);
    }

    /**
     * Этот тест проверяет, что для неавторизованного пользователя невозможно получить список заказов.
     */
    @Test
    //@DisplayName("Get orders | unauthorized")
    @Description("Verify that unauthorized user cannot get order list")
    @Severity(SeverityLevel.BLOCKER)
    public void checkGetOrderListUnauthorizedUser() throws Exception {
        // Получаем список заказов без авторизации и ожидаем код ответа 401 Unauthorized
        Response response = baseSteps.sendGetOrderListAndGetResponse("");
        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED);
        baseSteps.checkErrorMessage(response, ErrorMessage.GET_ORDER_UNAUTHORIZED_ERROR_401);
    }
}