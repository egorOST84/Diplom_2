package orders.create;

import common.BaseSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.*;
import pojo.OrderRequest;
import pojo.User;

import static utilities.OrderGenerator.generateRandomOrder;
import static specs.RestAssuredSpecs.*;
import static utilities.UserGenerator.generateRandomUser;

@Feature("[Create order] POST api/orders")
public class PostCreateOrderVerificationTest {
    private static BaseSteps baseSteps;
    private static String accessToken;
    @BeforeClass
    public static void setUp() throws Exception {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }

    @Test
    @Ignore
    //@DisplayName("Create order | authorized")
    @Description("This test verifies that an order can be created after authorization")
    @Severity(SeverityLevel.BLOCKER)
    public void checkCreateOrderForAuthorizedUser() throws Exception {
        // Регистрируем нового пользователя
        User rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
        // Получаем access token
        accessToken = baseSteps.loginUserAndGetToken(rndUser);
        // Создаем заказ
        OrderRequest orderReq = generateRandomOrder(2);
        baseSteps.createNewOrder(orderReq, accessToken);
    }

    @After
    public void tearDown() throws Exception {
        baseSteps.deleteRegisteredUser(accessToken);
    }
}
