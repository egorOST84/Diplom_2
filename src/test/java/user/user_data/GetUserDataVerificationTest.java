package user.user_data;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import common.BaseTest;
import org.junit.After;
import org.junit.Test;
import pojo.user.User;

import static utilities.UserGenerator.generateRandomUser;

@Feature("[Get user data] GET api/auth/user")
public class GetUserDataVerificationTest extends BaseTest {
    private static String accessToken;

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

    /**
     * В методе tearDown() происходит удаление созданного тестом пользователя.
     */
    @After
    public void tearDown() {
        baseSteps.deleteRegisteredUser(accessToken);
    }
}
