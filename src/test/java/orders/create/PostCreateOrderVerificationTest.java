package orders.create;

import common.BaseSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import pojo.OrderRequest;
import pojo.User;

import static utilities.OrderGenerator.generateRandomOrder;
import static specs.RestAssuredSpecs.*;
import static utilities.UserGenerator.generateRandomUser;

@Feature("[Create order] POST api/orders")
public class PostCreateOrderVerificationTest {
    private static BaseSteps baseSteps;

    @BeforeClass
    public static void setUp() throws Exception {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }

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
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
        // Получаем access token
        String accessToken = baseSteps.loginUserAndGetToken(rndUser);
        // Создаем заказ с авторизацией и ожидаем успешный ответ
        OrderRequest orderReq = generateRandomOrder(2);
        baseSteps.createNewOrderAndVerifyResponse(orderReq, accessToken);
        // Удаляем пользователя
        baseSteps.deleteRegisteredUser(accessToken);
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