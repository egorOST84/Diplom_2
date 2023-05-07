package orders.create;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import common.BaseTest;
import org.junit.*;
import pojo.order.OrderRequest;
import pojo.user.User;

import static utilities.OrderGenerator.generateRandomOrder;
import static utilities.UserGenerator.generateRandomUser;

@Feature("[Create order] POST api/orders")
public class PostCreateTestVerificationTest extends BaseTest {

    /**
     * Этот тест проверяет, что заказ может быть создан после авторизации пользователя.
     */
    @Test
    @DisplayName("Create order | authorized")
    @Description("This test verifies that an order can be created after authorization")
    @Severity(SeverityLevel.BLOCKER)
    public void checkCreateOrderForAuthorizedUser() {
        // Регистрируем нового пользователя
        User rndUser = generateRandomUser();
        registrationSteps.registrationNewUserAndVerifyResponse(rndUser);
        // Получаем access token
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        // Создаем заказ с авторизацией и ожидаем успешный ответ
        OrderRequest orderReq = generateRandomOrder(2);
        baseSteps.createNewOrderAndVerifyResponse(orderReq, accessToken);
        // Удаляем пользователя
        userSteps.deleteRegisteredUser(accessToken);
    }

    /**
     * Этот тест проверяет, что заказ может быть создан без авторизации.
     */
    @Test
    @DisplayName("Create order | unauthorized")
    @Description("Verify that order cannot be created without authorization")
    @Severity(SeverityLevel.BLOCKER)
    public void checkCreateOrderUnauthorizedUser() {
        // Создаем заказ без авторизации и ожидаем успешный ответ
        OrderRequest orderReq = generateRandomOrder(2);
        baseSteps.createNewOrderAndVerifyResponse(orderReq, "");
    }
}