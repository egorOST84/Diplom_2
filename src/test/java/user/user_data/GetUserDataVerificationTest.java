package user.user_data;

import common.BaseSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import pojo.User;

import static specs.RestAssuredSpecs.*;
import static utilities.UserGenerator.generateRandomUser;

@Feature("[Get user data] GET api/auth/user")
public class GetUserDataVerificationTest {
    private static BaseSteps baseSteps;
    private static String accessToken;
    @BeforeClass
    public static void setUp() {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }

    /**
     * В методе tearDown() происходит удаление созданного тестом пользователя.
     */
    @After
    public void tearDown() {
        baseSteps.deleteRegisteredUser(accessToken);
    }

    /**
     * Этот тест проверяет, что данные пользователя могут быть получены после авторизации.
     */

    @Test
    @DisplayName("Get user data | authorized")
    @Description("This test verifies that user's can be received after authorization")
    @Severity(SeverityLevel.CRITICAL)
    public void checkGetUserDataAuthorized() {
        User rndUser = generateRandomUser();
        baseSteps.registrationNewUserAndVerifyResponse(rndUser);
        accessToken = baseSteps.loginUserAndGetToken(rndUser);
        baseSteps.getUserAndVerifyResponse(rndUser, accessToken);
    }
}
